import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DeviceService, Device } from '../services/device.service';

@Component({
  selector: 'app-device-summary',
  standalone: true,
  templateUrl: './device-summary.html'
})
export class DeviceSummary implements OnInit {

  device!: Device;
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
      next: (data) => {
        this.device = data;
        this.cdr.detectChanges();  // ✅ keep CDR
      }
    });
  }

  enableEdit() {
    this.editMode = true;
  }

  update() {
    this.deviceService.updateDevice(this.device.id, this.device).subscribe({
      next: (updated) => {
        this.device = updated;
        this.editMode = false;
        this.cdr.detectChanges();
      }
    });
  }

  delete() {
    this.deviceService.deleteDevice(this.device.id).subscribe(() => {
      this.router.navigate(['/devices']);
    });
  }

  addShelf(shelfPositionId: string) {
    this.router.navigate(['/shelves/create'], {
      queryParams: { shelfPositionId }
    });
  }
}
