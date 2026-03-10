import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  styleUrl: './home.css',
  //very small html that's why didn't created another file for it
  template: `
  <div class="home-container">
    <button routerLink="/devices">View All Devices</button>
    <button routerLink="/devices/create">Create Device</button>
    <button routerLink="/shelves">View All Shelves</button>
    </div>
  `
})
export class Home {}
