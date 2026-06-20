import { Component, signal, ChangeDetectorRef, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { httpResource } from '@angular/common/http';
import { rxResource } from '@angular/core/rxjs-interop';

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
  organization = signal<any>(null);
  private cdr = inject(ChangeDetectorRef);

  loadTree(id: number) {
    this.http.get<any>(`http://localhost:8080/api/organizzazioni/${id}/tree`).subscribe({
      next: (res) => {
        if (res && res.contenitori) {
            res.contenitori.sort((a: any, b: any) => a.ordine - b.ordine);
        }
        this.organization.set({ ...res });

        // 3. 🚀 FORZA L'AGGIORNAMENTO: Dice ad Angular di ridisegnare la pagina ADESSO
        this.cdr.detectChanges();

        console.log('Albero caricato:', res);
      },
      error: (err) => console.error(err)
    });
  }

}
