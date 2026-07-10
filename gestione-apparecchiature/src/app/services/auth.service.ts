import {Injectable, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {Router} from '@angular/router';

@Injectable({providedIn:'root'})
export class AuthService {

  private api='http://localhost:8080/auth';

  public isLoggedIn = signal<boolean>(this.getCookie('is-logged-in') === 'true');

  constructor(private http:HttpClient, private router: Router){}

  // Funzione nativa per leggere un cookie specifico
  private getCookie(name: string): string | null {
    const nameLenPlus = (name.length + 1);
    return document.cookie
      .split(';')
      .map(c => c.trim())
      .filter(cookie => {
        return cookie.substring(0, nameLenPlus) === `${name}=`;
      })
      .map(cookie => {
        return decodeURIComponent(cookie.substring(nameLenPlus));
      })[0] || null;
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.api}/login`, { username, password })
      .pipe(
        tap(r => {
          this.isLoggedIn.set(true);
          localStorage.setItem('roles', r.roles);
        })
      );
  }

  logout() {
    this.http.post(`${this.api}/logout`, {}).subscribe({
      next: () => {
        this.isLoggedIn.set(false);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Errore durante il logout:', err);
      }
    });
  }

}
