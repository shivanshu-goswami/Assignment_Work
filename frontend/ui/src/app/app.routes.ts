import { Routes } from '@angular/router';
import {Devices} from './devices/devices';

export const routes: Routes = [
  {path:'',redirectTo:'devices',pathMatch:'full'},
  {path:'devices',component:Devices}
];
