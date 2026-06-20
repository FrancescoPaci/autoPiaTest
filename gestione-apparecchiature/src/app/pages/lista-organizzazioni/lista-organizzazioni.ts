import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { httpResource } from '@angular/common/http';

@Component({
  standalone: true,
  selector: 'app-lista-organizzazioni',
  imports: [],
  templateUrl: './lista-organizzazioni.html',
  styleUrl: './lista-organizzazioni.css',
})
export class ListaOrganizzazioniComponent { // 🚀 1. Implementiamo OnInit per far partire la chiamata all'avvio

  // Usiamo lo stile moderno con inject() al posto del costruttore classico
  private http = inject(HttpClient);

  organizationsResource = httpResource<any[]>(() => `http://localhost:8080/api/organizzazioni`);

  organizations: any[] = [];
  organization: any = null;

  loadTree(id: number) {
    this.http.get<any>(`http://localhost:8080/api/organizzazioni/${id}/tree`).subscribe({
      next: (response) => {
        this.organization = response;
        console.log('Albero caricato:', this.organization);
      },
      error: (err) => {
        console.error('Errore durante il caricamento dell\'albero:', err);
      }
    });
  }

}
