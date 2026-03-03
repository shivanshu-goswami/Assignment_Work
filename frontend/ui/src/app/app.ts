import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `
    <h1>Assignment Application</h1>
    <hr />
    <router-outlet></router-outlet>
  `
})
export class AppComponent {}
