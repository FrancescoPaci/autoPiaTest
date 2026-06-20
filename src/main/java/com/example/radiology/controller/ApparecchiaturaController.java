package com.example.radiology.controller;

import com.example.radiology.entity.Organizzazione;
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

    @PostMapping("/apparecchiatura")
    public Map<String, String> create() {
        return Map.of("status", "created");
    }

    @GetMapping("/organizzazioni/{id}/tree")
    public Organizzazione tree(@PathVariable Long id) {
        // 1. Recupera l'organizzazione dal database
        Organizzazione org = organizzazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizzazione non trovata con id: " + id));
        return org;
    }

    @GetMapping("/organizzazioni")
    public List<Organizzazione> getAllOrganizations() {
        return organizzazioneRepository.findAll();
    }
}