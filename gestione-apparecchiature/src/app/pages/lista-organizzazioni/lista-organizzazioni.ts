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

  // Risorsa per tutte le organizzazioni
  organizations = httpResource<any[]>(() => `http://localhost:8080/api/organizzazioni`);

  // Cambiato l'unione del tipo a undefined per coerenza
  idOrganizzation = signal<number | undefined>(undefined);

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
