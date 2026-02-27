import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Device {
  id?: string;
  deviceName: string;
  partNumber: string;
  buildingName: string;
  deviceType: string;
  //optional because during create we send it but during update we don't send it
  numberOfShelfPositions?:number;
}
//this makes the service globally available, like same as @Service in spring
@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  //this is our backend  endpoint, it must match our spring controller mapping
  private apiUrl = 'http://localhost:8080/devices';

  //angular built-in tool to call backend APIs
  constructor(private http: HttpClient) {}

  //Angular does async calls using RxJS Observables.
  //We can think of it like:"Future result that will come later"
  //like same as Completable future
  getAllDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(this.apiUrl);
  }

  getDeviceById(id: string): Observable<Device> {
    return this.http.get<Device>(`${this.apiUrl}/${id}`);
  }

  createDevice(device: Device): Observable<Device> {
    return this.http.post<Device>(this.apiUrl, device);
  }

  updateDevice(id: string, device: Device): Observable<Device> {
    return this.http.put<Device>(`${this.apiUrl}/${id}`, device);
  }

  deleteDevice(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
