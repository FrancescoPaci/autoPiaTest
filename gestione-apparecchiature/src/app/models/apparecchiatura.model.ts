export interface Apparecchiatura {
  id?: number | null; // Il punto di domanda indica che è opzionale (es. a schermo prima del salvataggio non c'è l'ID)
  nome: string;
  tipologia: string;
  numeroSerie: string;
  dataInstallazione: string; // LocalDate di Java viene mappato come stringa (formato "YYYY-MM-DD")

  // Specchiamo gli oggetti relazionali di Spring Boot
  organizzazione: { id: number } | null;
  contenitore: { id: number } | null;
}
