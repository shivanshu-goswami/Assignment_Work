import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DeviceService } from '../services/device.service';

@Component({
  selector: 'app-device-create',
  standalone: true,
  imports: [CommonModule,FormsModule],
  styleUrls: ['./device-create.css'],
  templateUrl: './device-create.html'
})
export class DeviceCreate {

  device: any = {
    deviceName: '',
    partNumber: '',
    buildingName: '',
    deviceType: '',
    numberOfShelfPositions: 1
  };

  constructor(
    private deviceService: DeviceService,
    private router: Router
  ) {}

  create() {
    this.deviceService.createDevice(this.device).subscribe(() => {
      this.router.navigate(['/devices']);
    });
  }
}
