import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http'; // 1️⃣ AGGIUNTO QUESTO IMPORT FONDAMENTALE

@Component({
  standalone: true,
  selector: 'app-lista-organizzazioni',
  imports: [], // Se usi solo la nuova sintassi di controllo flusso (es. @for, @if), puoi lasciarlo vuoto
  templateUrl: './lista-organizzazioni.html',
  styleUrl: './lista-organizzazioni.css',
})
export class ListaOrganizzazioniComponent {

  organizzazioni: any[] = [];

  // Ora HttpClient viene riconosciuto correttamente
  constructor(private http: HttpClient) {}

  loadTree(id: number) {
    this.http.get<any>(`http://localhost:8080/api/organizzazioni/${id}/tree`).subscribe({
      next: (response) => {
        // Se l'endpoint /tree restituisce già un array, va bene così.
        // Se restituisce un oggetto singolo, ricordati di racchiuderlo in parentesi quadre: [response]
        this.organizzazioni = response;
        console.log('Dati caricati:', this.organizzazioni);
      },
      error: (err) => {
        console.error('Errore durante il caricamento dell\'albero:', err);
      }
    });
  }
}
