import { Component, input, computed, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Apparecchiatura } from '../../models/apparecchiatura.model';

@Component({
  standalone: true,
  selector: 'app-crea-attrezzatura',
  imports: [FormsModule],
  templateUrl: './crea-attrezzatura.html',
  styleUrl: './crea-attrezzatura.css',
})
export class CreaAttrezzaturaComponent {

  organizations = input<any>(null);
  private http = inject(HttpClient);

  // 🚀 Il tuo oggetto inizializzato pronto a raccogliere i dati del form
attrezzatura: Apparecchiatura = {
    nome: '',
    tipologia: '',
    numeroSerie: '',
    dataInstallazione: '',
    organizzazione: null,
    contenitore: null
  };

  // Signals per la gestione degli stati grafici (esclusione e disabilitazione)
  selectedOrganizationId = signal<number | null>(null);
  selectedContainerId = signal<number | null>(null);

  isOrganizationDisabled = computed(() => this.selectedContainerId() !== null);
  isContainerDisabled = computed(() => this.selectedOrganizationId() !== null);

// Sincronizzazione al cambio dell'organizzazione
  onOrganizationChange(value: any) {
    const id = (value === 'null' || value === null) ? null : Number(value);

    this.attrezzatura.organizzazione = id ? { id: id } : null;

    if (id !== null) {
      this.selectedContainerId.set(null);
      this.attrezzatura.contenitore = null; // Svuota il contenitore alternativo
    }
  }

  // Sincronizzazione al cambio del contenitore
  onContainerChange(value: any) {
    const id = (value === 'null' || value === null) ? null : Number(value);

    this.attrezzatura.contenitore = id ? { id: id } : null;

    if (id !== null) {
      this.selectedOrganizationId.set(null);
      this.attrezzatura.organizzazione = null; // Svuota l'organizzazione alternativa
    }
  }

  salva() {
    this.http.post<any>(`http://localhost:8080/api/apparecchiatura`, this.attrezzatura)
      .subscribe({
        next: (res) => {
          alert('Apparecchiatura salvata correttamente!');
          this.resetForm()
        },
        error: (err) => {
          if(err.status === 403) {
             alert('L\'utente non ha i permessi per eseguire l\'operazione');
          } else {
            alert('Operazione non riuscita');
          }
        }
      });
  }

  resetForm() {
    this.attrezzatura = {
      nome: '',
      tipologia: '',
      numeroSerie: '',
      dataInstallazione: '',
      organizzazione: null,
      contenitore: null
    };

    this.selectedOrganizationId.set(null);
    this.selectedContainerId.set(null);
  }

  // --- Data Sourcing ---
  listaOrganizations = computed(() => {
    const orgs = this.organizations();
    if (!orgs || !Array.isArray(orgs)) return [];
    return orgs.map((org: any) => ({ id: org.id, nome: org.nome }));
  });

  listaContenitori = computed(() => {
    const orgs = this.organizations();
    if (!orgs || !Array.isArray(orgs)) return [];
    return orgs
      .flatMap((org: any) => org.contenitori || [])
      .map((cont: any) => ({ id: cont.id, nome: cont.nome }));
  });
}
