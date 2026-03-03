import { Routes } from '@angular/router';

export const routes: Routes = [

  {
    path: '',
    loadComponent: () =>
      import('./home/home').then(m => m.Home)
  },

  {
    path: 'devices',
    loadComponent: () =>
      import('./devices/devices').then(m => m.Devices)
  },

  {
    path: 'devices/create',
    loadComponent: () =>
      import('./devices/device-create').then(m => m.DeviceCreate)
  },

  {
    path: 'devices/:id',
    loadComponent: () =>
      import('./devices/device-summary').then(m => m.DeviceSummary)
  },

  {
    path: 'shelves',
    loadComponent: () =>
      import('./shelves/shelves').then(m => m.Shelves)
  },

  {
    path: 'shelves/create',
    loadComponent: () =>
      import('./shelves/shelf-create').then(m => m.ShelfCreate)
  },

  {
    path: 'shelves/:id',
    loadComponent: () =>
      import('./shelves/shelf-summary').then(m => m.ShelfSummary)
  }

];
