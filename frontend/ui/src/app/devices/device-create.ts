import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DeviceService } from '../services/device.service';

@Component({
  selector: 'app-device-create',
  //this tells that this component did not belong to any module and it can work independently
  standalone: true,
  imports: [CommonModule,FormsModule],
  styleUrls: ['./device-create.css'],
  templateUrl: './device-create.html'
})

export class DeviceCreate {

//through ngModel, data binding of input field is done, so whatever user writes will automatically
//get stored in device properties like name, part number and then we send this device having data
//to service layer create api where it is sent to backend as a request
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
