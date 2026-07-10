import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';

export const authGuard:CanActivateFn=()=>{
 const router=inject(Router);
 const roles=(localStorage.getItem('roles')||'');
 console.log(localStorage.getItem('roles'))
 if(roles.includes('ROLE_ADMIN')||roles.includes('ROLE_USER')){
   return true;
 }
 router.navigate(['/unauthorized']);
 return false;
};
