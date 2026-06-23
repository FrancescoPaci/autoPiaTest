package com.example.radiology.controller;

import com.example.radiology.entity.Apparecchiatura;
import com.example.radiology.entity.Organizzazione;
import com.example.radiology.repository.ApparecchiaturaRepository;
import com.example.radiology.repository.OrganizzazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApparecchiaturaController {

    @Autowired
    private OrganizzazioneRepository organizzazioneRepository;
    @Autowired
    private ApparecchiaturaRepository apparecchiaturaRepository;

    @PostMapping("/apparecchiatura")
    public Map<String, String> create(@RequestBody Apparecchiatura apparecchiatura) {
        System.out.println("Apparecchiatura ricevuta: " + apparecchiatura.getNome());
        apparecchiaturaRepository.save(apparecchiatura);
        return Map.of("status", "created");
    }

    @GetMapping("/organizzazioni/{id}/tree")
    public Organizzazione tree(@PathVariable Long id) {
        // Recupera l'organizzazione dal database
        return organizzazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizzazione non trovata con id: " + id));
    }

    @GetMapping("/organizzazioni")
    public List<Organizzazione> getAllOrganizations() {
        return organizzazioneRepository.findAll();
    }
}