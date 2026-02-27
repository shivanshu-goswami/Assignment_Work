import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common';
import { DeviceService, Device } from '../services/device.service';

@Component({
  selector: 'app-devices',
  standalone:true,
  imports:[CommonModule],
  templateUrl: './devices.html',
  styleUrls: ['./devices.css']
})
export class Devices implements OnInit {

  devices: Device[] = [];

  constructor(private deviceService: DeviceService) {}

  ngOnInit(): void {
    this.loadDevices();
  }

  loadDevices(): void {
    console.log("Loading devices...");

    this.deviceService.getAllDevices().subscribe({
      next: (data: Device[]) => {
        console.log("Backend response:", data);
        this.devices = data;
      },
      error: (err) => {
        console.error("ERROR from backend:", err);
      }
    });
  }
  deleteDevice(id:string):void{
    this.deviceService.deleteDevice(id).subscribe(()=>{
      this.loadDevices();//this refresh list after delete
    })
  }
}
