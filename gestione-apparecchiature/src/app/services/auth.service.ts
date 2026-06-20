
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({providedIn:'root'})
export class AuthService {
  private api='http://localhost:8080/auth';

  constructor(private http:HttpClient){}

login(username: string, password: string): Observable<any> {
  return this.http.post<any>(`${this.api}/login`, { username, password })
    .pipe(
      tap(r => {
        localStorage.setItem('token', r.token);
        localStorage.setItem('roles', r.roles);
      })
    );
}

  logout():Observable<any>{
    localStorage.removeItem('token');
    return this.http.post(`${this.api}/logout`,{});
  }

  getToken(){ return localStorage.getItem('token'); }
  isLogged(){ return !!this.getToken(); }
}
