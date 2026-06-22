import { Component, signal, computed, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  private readonly auth = inject(AuthService);
  readonly token = computed(() => this.auth.isAuthenticated());

  logout() {
     this.auth.logout();
  }

}
