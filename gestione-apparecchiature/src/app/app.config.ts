import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
// 🚀 1. Importa il client HTTP e la funzione per iniettare gli interceptor
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { jwtInterceptor } from './interceptors/jwt.interceptor';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),

    // 🚀 3. CONFIGURAZIONE CORRETTA: Abilitiamo HttpClient e gli passiamo il tuo interceptor
    provideHttpClient(
      withInterceptors([jwtInterceptor])
    )
  ]
};
