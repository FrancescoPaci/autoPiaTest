package com.example.radiology.controller;

import com.example.radiology.entity.Apparecchiatura;
import com.example.radiology.entity.Organizzazione;
import com.example.radiology.repository.ApparecchiaturaRepository;
import com.example.radiology.repository.OrganizzazioneRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api")
public class ApparecchiaturaController {

    private final OrganizzazioneRepository organizzazioneRepository;
    private final ApparecchiaturaRepository apparecchiaturaRepository;
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public ApparecchiaturaController(OrganizzazioneRepository organizzazioneRepository,
                                     ApparecchiaturaRepository apparecchiaturaRepository) {
        this.organizzazioneRepository = organizzazioneRepository;
        this.apparecchiaturaRepository = apparecchiaturaRepository;
    }

    // 1. Il FE chiama questo endpoint quando entra nella pagina per "ascoltare"
    @GetMapping(value = "/emitter-apparecchiatura", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter inscribeToEventEmitter() {
        // Timeout di 10 minuti (600.000 ms)
        SseEmitter emitter = new SseEmitter(600_000L);
        this.emitters.add(emitter);
        // Rimuovi l'emitter quando il client chiude la pagina o va in timeout
        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));
        return emitter;
    }

    public void updateEmitter(Long apparecchiaturaId) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("apparecchiatura-added")
                        .data(apparecchiaturaId));
            } catch (Exception e) {
                emitters.remove(emitter);
            }
        }
    }

    @PostMapping("/apparecchiatura")
    public Map<String, String> create(@RequestBody Apparecchiatura apparecchiatura) {
        System.out.println("Apparecchiatura ricevuta: " + apparecchiatura.getNome());
        apparecchiaturaRepository.save(apparecchiatura);
        updateEmitter(apparecchiatura.getId());
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