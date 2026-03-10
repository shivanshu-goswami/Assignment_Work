import {DeviceResponse,DeviceRequest} from '../services/device.service'
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router,RouterLink} from '@angular/router';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import { DeviceService } from '../services/device.service';

@Component({
  selector: 'app-device-summary',
  standalone: true,
  imports: [CommonModule,FormsModule],
  styleUrls: ['./device-summary.css'],
  templateUrl: './device-summary.html'
})
export class DeviceSummary implements OnInit {
   // i need this device for edit Mode in template
  device!: DeviceRequest;
  // i need this device response for view mode in template
  deviceResponse!:DeviceResponse;
  //initially we have view mode where we show summary that's why it's initialised with false
  editMode = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private deviceService: DeviceService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    //fetching Route params here and then using it to fill data in my device which i initialised above
    // so that i can use its reference in html page and can show the device summary
    const id = this.route.snapshot.paramMap.get('id')!;
    this.deviceService.getDeviceById(id).subscribe({
      next: (data:DeviceResponse) => {
        //saved both device and deviceResponse for view and edit mode as component loads
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
        //once updated we need to update data in our initialised device and deviceResponse also
        //otherwise updated data wont get rendered on ui even when changes are done in Database
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
    //we need deviceReponse in delete because delete soft deletes device,sp,shelf and all this detail is in deviceResponse
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
  addExistingShelf(shelfPositionId: string){
    this.router.navigate(['/shelves/addShelf'],{
      queryParams:{shelfPositionId}
      });
    }
}

//note: device!-> this exclaimation is Definite Assignment Assertion Operator, it basically tells
//typescript that variable will definitely be initialised later even if compiler cannot see it
//cause normally typescript expects variable to be initialised immediately that's why we used it here
//otherwise it would have thrown error
