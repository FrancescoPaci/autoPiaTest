import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({providedIn:'root'})
export class AuthService {

  private api='http://localhost:8080/auth';

  readonly isAuthenticated = signal(!!localStorage.getItem('token'));

  constructor(private http:HttpClient, private router: Router){}

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.api}/login`, { username, password })
      .pipe(
        tap(r => {
          localStorage.setItem('token', r.token);
          localStorage.setItem('roles', r.roles);
          this.isAuthenticated.set(true);
        })
      );
  }

  logout() {
    localStorage.removeItem('token');
    this.isAuthenticated.set(false);
    this.router.navigate(['/login']);
  }

  getToken(){ return localStorage.getItem('token'); }
  isLogged(){ return !!this.getToken(); }
}
