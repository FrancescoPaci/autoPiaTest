import {HttpInterceptorFn} from '@angular/common/http';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  // Clona la richiesta e aggiunge withCredentials
  const credentialReq = req.clone({
    withCredentials: true
  });
  return next(credentialReq);
};
