package com.example.radiology.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {

    // 🚀 Tornati alla lista concorrente semplice e anonima
    private final Set<SseEmitter> emitters = ConcurrentHashMap.newKeySet();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(180_000L);

        // 1. Quando scatta il timeout, completalo in modo pulito e rimuovilo dal Set
        emitter.onTimeout(() -> {
            emitter.complete(); // Chiude il canale in modo ordinato senza lanciare eccezioni distruttive
            this.emitters.remove(emitter);
        });

        // 2. Se si rompe la connessione per qualsiasi altro motivo di rete
        emitter.onError(ex -> {
            emitter.complete();
            this.emitters.remove(emitter);
        });

        // 3. Rimuovilo anche se si completa normalmente
        emitter.onCompletion(() -> this.emitters.remove(emitter));

        this.emitters.add(emitter);
        return emitter;
    }

    public void broadcast(String eventName, Object data) {
        // Ciclo sicuro su tutti gli emitter attivi
        for (SseEmitter emitter : this.emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
            } catch (IOException e) {
                // 🧹 Se il client ha chiuso la pagina o fatto logout, lo puliamo all'istante
                this.emitters.remove(emitter);
            } catch (Exception e) {
                this.emitters.remove(emitter);
            }
        }
    }

}