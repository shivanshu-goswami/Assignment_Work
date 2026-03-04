import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  styleUrl: './home.css',
  template: `
  <div class="home-container">
    <button routerLink="/devices">View All Devices</button>
    <button routerLink="/devices/create">Create Device</button>
    <button routerLink="/shelves">View All Shelves</button>
    </div>
  `
})
export class Home {}
