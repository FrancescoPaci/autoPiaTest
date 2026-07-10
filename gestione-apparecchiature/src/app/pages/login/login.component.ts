import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule], // Ora FormsModule viene riconosciuto correttamente
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username = '';
  password = '';

  // Ora sia AuthService che Router sono tipizzati correttamente
  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  login() {
    this.auth.login(this.username, this.password).subscribe({
      next: (response) => {
        console.log('Login effettuato con successo!', response);
        // Ricordati di verificare che la rotta in app.routes.ts sia esattamente 'listaOrganizzazioni'
        this.router.navigate(['/listaOrganizzazioni']);
      },
      error: (err) => {
        console.error('Errore durante il login', err);
        alert('Credenziali non valide!');
      }
    });
  }
}
