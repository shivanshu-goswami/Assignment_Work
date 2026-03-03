import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DeviceService } from '../services/device.service';

@Component({
  selector: 'app-device-create',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './device-create.html'
})
export class DeviceCreate {

  device: any = {
    name: '',
    partNumber: '',
    building: '',
    type: '',
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
