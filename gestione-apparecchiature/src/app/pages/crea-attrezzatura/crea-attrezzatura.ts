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

  private http = inject(HttpClient);
  organizations = input<any>(null);
  selectedOrganizationId = signal<number | null>(null);
  selectedContainerId = signal<number | null>(null);
  attrezzatura: Apparecchiatura = {
      nome: '',
      tipologia: '',
      numeroSerie: '',
      dataInstallazione: '',
      organizzazione: null,
      contenitore: null
  };

  onOrganizationChange(value: any) {
    const id = Number.isInteger(value) ? Number(value) : null
    this.attrezzatura.organizzazione = id ? { id: id } : null;
    if(id) {
      this.selectedContainerId.set(null);
      this.attrezzatura.contenitore = null;
    }
  }

  onContainerChange(value: any) {
    const id = Number.isInteger(value) ? Number(value) : null
    this.attrezzatura.contenitore = id ? { id: id } : null;
    if (id) {
      this.selectedOrganizationId.set(null);
      this.attrezzatura.organizzazione = null;
    }
  }

  save() {
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
