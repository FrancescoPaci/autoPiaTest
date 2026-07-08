import { Component, signal } from '@angular/core';
import { httpResource } from '@angular/common/http';
import { CreaAttrezzaturaComponent } from '../crea-attrezzatura/crea-attrezzatura';

@Component({
  standalone: true,
  selector: 'app-lista-organizzazioni',
  imports: [CreaAttrezzaturaComponent],
  templateUrl: './lista-organizzazioni.html',
  styleUrl: './lista-organizzazioni.css',
})
export class ListaOrganizzazioniComponent {

  creaAttrezzatura = false;
  organizations = httpResource<any[]>(() => `http://localhost:8080/api/organizzazioni`);
  idOrganizzation = signal<number | undefined>(undefined);
  private eventSource?: EventSource;

  ngOnInit() {
    // Inizializza la connessione quando il componente viene creato
    this.connectToStream();
  }

  private connectToStream() {
    this.eventSource = new EventSource('http://localhost:8080/api/emitter-apparecchiatura', {
      withCredentials: true
    });
    this.eventSource.addEventListener('apparecchiatura-added', (event) => {
        const seatData = JSON.parse(event.data);
        this.organization.reload();
    });
    this.eventSource.onerror = (error) => {
        console.error('Errore nella connessione SSE:', error);
    };
  }

  ngOnDestroy() {
    // ⚠️ FONDAMENTALE: Chiudi la connessione quando l'utente cambia pagina,
    // altrimenti il browser lascerà il canale aperto all'infinito!
    if (this.eventSource) {
      this.eventSource.close();
    }
  }

  // 🚀 Esplicitiamo <any> qui per forzare l'oggetto finale a essere di tipo 'any'
  organization = httpResource<any>(
    () => {
      const id = this.idOrganizzation();
      if (!id) return undefined;

      return {
        url: `http://localhost:8080/api/organizzazioni/${id}/tree`,
        transform: (res: any) => {
          if (res?.contenitori) {
            res.contenitori.sort((a: any, b: any) => a.ordine - b.ordine);
          }
          return res;
        }
      };
    }
  );

}
