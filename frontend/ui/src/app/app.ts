import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `
  <div class="app-header">
    <h1>Assignment Application</h1>
  </div>
    <hr />
    <router-outlet></router-outlet>
  `
})
export class AppComponent {}
