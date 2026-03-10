import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { AppComponent } from './app/app';
import { routes } from './app/app.routes';

// we are using standalone(it don't belong to any module and can run independently)
//component so our angular starts from here(that is main.ts), it tells "start angular, load app component, apply appconfig"
bootstrapApplication(AppComponent, {
  providers: [provideRouter(routes)]
});
