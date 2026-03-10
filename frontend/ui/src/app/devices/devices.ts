import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { DeviceService, DeviceResponse} from '../services/device.service';

@Component({
  selector: 'app-devices',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './devices.html',
  //multiple css file might be there , in that case we use this 'styleUrls', just added here to
  //remember this method, otherwise not needed here
  styleUrls: ['./devices.css']
})
export class Devices implements OnInit {
//declared an empty list of type DeviceResponse which will be returned by backend
  devices: DeviceResponse[] = [];

  constructor(
    //that injectable  in service actually injects it here so that we can call service methods through this file aslo
    private deviceService: DeviceService,
    private router: Router,
    //for manual UI refresh
    private cdr: ChangeDetectorRef
  ) {}

//life cycle method( which gets called automatically as component loads and I want my table to show me
//all devices as soon as page loads that's why I called my load devices from here
  ngOnInit(): void {
    this.loadDevices();
  }

  loadDevices() {
    //service method will give data in observable i.e, will give data as soon as it arrives from backend
    //so subscribe means start listening to observable and execute code when data arrives
    this.deviceService.getAllDevices().subscribe({
      //next,error,complete-> 3 types of signals(notification) that an observable can send to subscribe
      // next->new data arrived, error->something went wrong, complete-> stream finished
      next: (data) => {
        this.devices = data;
        this.cdr.detectChanges();   // forcing refresh cause data was not getting rendered on template, possible reason could be standalone component use
      }
    //i can also use "error:" here in case above response isn't received or something went wrong
    });
  }

  viewDevice(id: string) {
    this.router.navigate(['/devices', id]);
  }

  deleteDevice(id: string) {
    this.deviceService.deleteDevice(id).subscribe(() => {
      this.loadDevices();
    });
  }
}
