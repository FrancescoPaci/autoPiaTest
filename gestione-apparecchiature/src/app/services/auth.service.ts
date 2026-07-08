import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({providedIn:'root'})
export class AuthService {

  private api='http://localhost:8080/auth';

  readonly isAuthenticated = signal(false);

  constructor(private http:HttpClient, private router: Router){}

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.api}/login`, { username, password })
      .pipe(
        tap(r => {
          localStorage.setItem('roles', r.roles);
          this.isAuthenticated.set(true);
        })
      );
  }

  logout() {
    // Nota: l'interceptor aggiungerà automaticamente { withCredentials: true }
    this.http.post(`${this.api}/logout`, {}).subscribe({
      next: () => {
        console.log('Cookie rimosso dal browser!');
        this.isAuthenticated.set(false);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Errore durante il logout:', err);
      }
    });
  }

}
