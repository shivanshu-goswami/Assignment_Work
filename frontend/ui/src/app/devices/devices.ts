import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { DeviceService, Device } from '../services/device.service';

@Component({
  selector: 'app-devices',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './devices.html',
  styleUrls: ['./devices.css']
})
export class Devices implements OnInit {

  devices: Device[] = [];

  constructor(
    private deviceService: DeviceService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadDevices();
  }

  loadDevices() {
    this.deviceService.getAllDevices().subscribe({
      next: (data) => {
        this.devices = data;
        this.cdr.detectChanges();   // ✅ forcing refresh as you requested
      }
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
