package com.example.radiology.controller;

import com.example.radiology.entity.Apparecchiatura;
import com.example.radiology.entity.Organizzazione;
import com.example.radiology.repository.ApparecchiaturaRepository;
import com.example.radiology.repository.OrganizzazioneRepository;
import com.example.radiology.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApparecchiaturaController {

    private final OrganizzazioneRepository organizzazioneRepository;
    private final ApparecchiaturaRepository apparecchiaturaRepository;
    private final NotificationService notificationService;

    @PostMapping("/apparecchiatura")
    public Map<String, String> create(@RequestBody Apparecchiatura apparecchiatura) {
        System.out.println("Apparecchiatura ricevuta: " + apparecchiatura.getNome());
        apparecchiaturaRepository.save(apparecchiatura);
        notificationService.broadcast("apparecchiatura-added", apparecchiatura.getId());
        return Map.of("status", "created");
    }

    @GetMapping("/organizzazioni/{id}/tree")
    public Organizzazione tree(@PathVariable Long id) {
        return organizzazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizzazione non trovata con id: " + id));
    }

    @GetMapping("/organizzazioni")
    public List<Organizzazione> getAllOrganizations() {
        return organizzazioneRepository.findAll();
    }
}