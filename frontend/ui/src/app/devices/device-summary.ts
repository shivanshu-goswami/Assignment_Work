import {DeviceResponse,DeviceRequest} from '../services/device.service'
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router,RouterLink} from '@angular/router';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import { DeviceService } from '../services/device.service';

@Component({
  selector: 'app-device-summary',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterLink],
  templateUrl: './device-summary.html'
})
export class DeviceSummary implements OnInit {

  device!: DeviceRequest;
  deviceResponse!:DeviceResponse;
  editMode = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private deviceService: DeviceService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!;
    this.deviceService.getDeviceById(id).subscribe({
      next: (data:DeviceResponse) => {
        this.deviceResponse=data;

        this.device = {
          id: data.id,
          deviceName: data.name,
          partNumber: data.partNumber,
          buildingName: data.building,
          deviceType: data.type,
          numberOfShelfPositions: data.shelfPositions?.length || 1
        };
        this.cdr.detectChanges();
      }
    });
  }

  enableEdit() {
    this.editMode = true;
  }

  update() {
    if(!this.device.id) return;
    this.deviceService.updateDevice(this.device.id, this.device).subscribe({
      next: (updated) => {
        this.deviceResponse = updated;

        this.device = {
          id: updated.id,
          deviceName: updated.name,
          partNumber: updated.partNumber,
          buildingName: updated.building,
          deviceType: updated.type,
          numberOfShelfPositions: updated.shelfPositions?.length || 1
        };
        this.editMode = false;
        this.cdr.detectChanges();
      }
    });
  }

  delete() {
    if(!this.deviceResponse?.id) return;
    this.deviceService.deleteDevice(this.deviceResponse.id).subscribe(() => {
      this.router.navigate(['/devices']);
    });
  }

  addShelf(shelfPositionId: string) {
    this.router.navigate(['/shelves/create'], {
      queryParams: { shelfPositionId }
    });
  }
}
