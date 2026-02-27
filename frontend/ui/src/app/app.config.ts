import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';

import { routes } from './app.routes';

//without this angular cannot call backend
export const appConfig: ApplicationConfig = {
  //tells angular what services are available globally
  providers: [
    //this enable routing and tells angular to use routes defined in app.routes.ts
    provideRouter(routes),
    //this enable http functionality and allow me to call backend api,
    //without this angular cannot make REST calls
    provideHttpClient()
  ]
};
