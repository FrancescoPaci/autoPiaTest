package com.example.radiology.controller;

import com.example.radiology.entity.Apparecchiatura;
import com.example.radiology.entity.Organizzazione;
import com.example.radiology.repository.ApparecchiaturaRepository;
import com.example.radiology.repository.OrganizzazioneRepository;
import com.example.radiology.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApparecchiaturaController.class)
class ApparecchiaturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ApparecchiaturaRepository apparecchiaturaRepository;

    @MockitoBean
    private OrganizzazioneRepository organizzazioneRepository;

    @MockitoBean
    private JwtService jwtService;

    @Test
    @WithMockUser(username = "user", roles = "ADMIN")
    void testCreateApparecchiatura_Success() throws Exception {
        Apparecchiatura app = new Apparecchiatura();
        app.setNome("TAC Generale");
        app.setTipologia("TAC");
        app.setNumeroSerie("SN-123456");

        Mockito.when(apparecchiaturaRepository.save(Mockito.any(Apparecchiatura.class))).thenReturn(app);

        mockMvc.perform(post("/api/apparecchiatura")
                        .with(csrf()) // 2. Inietta un token CSRF valido per superare il blocco 403
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(app)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("created"));
    }

    // --- TEST PER GET ALL ORGANIZZAZIONI ---
    @Test
    @WithMockUser(username = "user", roles = "ADMIN")
    // 🚀 Simula l'utente autenticato per evitare il 403
    void testGetAllOrganizations_Success() throws Exception {
        Organizzazione org1 = new Organizzazione();
        org1.setId(1L);
        org1.setNome("Ospedale San Raffaele");

        Organizzazione org2 = new Organizzazione();
        org2.setId(2L);
        org2.setNome("Clinica Diagnostica Avanzata");

        Mockito.when(organizzazioneRepository.findAll()).thenReturn(List.of(org1, org2));

        mockMvc.perform(get("/api/organizzazioni")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Ospedale San Raffaele"));
    }

    // --- TEST PER GET TREE (BY ID) ---
    @Test
    @WithMockUser(username = "user", roles = "ADMIN")
    // 🚀 Simula l'utente autenticato per evitare il 403
    void testGetOrganizationTree_Success() throws Exception {
        Organizzazione org = new Organizzazione();
        org.setId(10L);
        org.setNome("Clinica Centrale");

        Mockito.when(organizzazioneRepository.findById(10L)).thenReturn(Optional.of(org));

        mockMvc.perform(get("/api/organizzazioni/{id}/tree", 10L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.nome").value("Clinica Centrale"));
    }

    // --- TEST CASO DI ERRORE (404/500) ---
    @Test
    @WithMockUser(username = "user", roles = "ADMIN")
    void testGetOrganizationTree_NotFound() {
        // Il controller si aspetta l'id 1 (come si legge dal log di errore), simuliamo l'Optional vuoto
        Mockito.when(organizzazioneRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Intercettiamo la ServletException causata dalla RuntimeException cruda
        org.junit.jupiter.api.Assertions.assertThrows(jakarta.servlet.ServletException.class, () -> {
            mockMvc.perform(get("/api/organizzazioni/{id}/tree", 1L)
                    .contentType(MediaType.APPLICATION_JSON));
        });
    }

}