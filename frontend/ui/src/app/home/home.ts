import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  template: `
    <button routerLink="/devices">View All Devices</button>
    <button routerLink="/devices/create">Create Device</button>

    <br /><br />

    <button routerLink="/shelves">View All Shelves</button>
    <button routerLink="/shelves/create">Create Shelf</button>
  `
})
export class Home {}
