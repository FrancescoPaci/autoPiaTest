package com.example.radiology.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationService {

    // 🚀 Tornati alla lista concorrente semplice e anonima
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        // ⏱️ Timeout a 3 minuti (180.000 ms) per evitare l'accumulo di connessioni morte
        SseEmitter emitter = new SseEmitter(180_000L);

        this.emitters.add(emitter);

        // Rimozione automatica dalla lista quando la connessione si chiude o va in timeout
        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));

        return emitter;
    }

    public void broadcast(String eventName, Object data) {
        // Ciclo sicuro su tutti gli emitter attivi
        for (SseEmitter emitter : emitters) {
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