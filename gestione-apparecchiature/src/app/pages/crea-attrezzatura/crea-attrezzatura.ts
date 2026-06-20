import { Component, input, computed, signal, effect } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-crea-attrezzatura',
  imports: [FormsModule],
  templateUrl: './crea-attrezzatura.html',
  styleUrl: './crea-attrezzatura.css',
})
export class CreaAttrezzaturaComponent {

    organizations = input<any>(null);

  constructor() {
      // 🚀 L'effetto "ascolta" il signal e logga AUTOMATICAMENTE ogni volta che cambia!
      effect(() => {
        console.log('Organizations è cambiato! Nuovo valore:', this.organizations());
      });
    }

  selectedOrganizationId = signal<number | null>(null);
  selectedContainerId = signal<number | null>(null);

  // 2. Adesso gestiamo l'oggetto singolo: creiamo un array con la sola organizzazione corrente
  listaOrganizations = computed(() => {
    const org = this.organizations();
    if (!org) return [];

    // Ritorniamo un array contenente solo l'organizzazione singola spacchettata
    return [{
      id: org.id,
      nome: org.nome
    }];
  });

  // 3. Estraiamo i contenitori direttamente dall'oggetto singolo senza flatMap
  listaContenitori = computed(() => {
    const org = this.organizations();
    if (!org || !org.contenitori) return [];

    // Prendiamo l'array dei contenitori dell'unica organizzazione e ne mappiamo id e nome
    return org.contenitori.map((cont: any) => ({
      id: cont.id,
      nome: cont.nome
    }));
  });

}
