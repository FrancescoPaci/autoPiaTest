
import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { ListaOrganizzazioniComponent } from './pages/lista-organizzazioni/lista-organizzazioni';
import { UnauthorizedComponent } from './pages/unauthorized/unauthorized.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
 {path:'login',component:LoginComponent},
 {path:'unauthorized',component:UnauthorizedComponent},
 {path:'listaOrganizzazioni',component:ListaOrganizzazioniComponent, canActivate:[authGuard]},
 {path:'',redirectTo:'login',pathMatch:'full'}
];
