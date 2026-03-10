import { Routes } from '@angular/router';

export const routes: Routes = [

  {
    path: '',
    //lazy loading, can do it simply also through component but can it can make my website slow
    //when there are many pages as it loads all pages when app starts
    loadComponent: () =>
    //here m is module so angular loads home.ts
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
    //dynamic route(route parameter and it will be passed on to component), we will fetch the id value param through activated route in device summary ts file
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
      import('./shelves/shelf-summary').then(m => m.ShelfSummaryComponent)
  }

];
