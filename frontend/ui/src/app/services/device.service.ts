import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Shelf {
  id: string;
  shelfName: string;
  partNumber: string;
}

export interface ShelfPosition {
  id: string;
  positionNumber: number;
  shelf: Shelf | null;
}

//I need it when i get a fetch a device using its id like when i view a particular device on summary page
export interface DeviceResponse {
  id: string;
  name: string;
  partNumber: string;
  building: string;
  type: string;
  shelfPositions: ShelfPosition[];
}

//i need it when i create and edit device
export interface DeviceRequest {
  id?: string;
  deviceName: string;
  partNumber: string;
  buildingName: string;
  deviceType: string;
  numberOfShelfPositions: number;
}

//it tells angular that this class is a service so like angular have the power to inject it anywhere
@Injectable({
  providedIn: 'root'  //we can think of it as creating a global variable or instance
})
export class DeviceService {

  private baseUrl = 'http://localhost:8080/devices';

  //this is angular class for making http requests like post,put,get,delete
  constructor(private http: HttpClient) {}

  //Http requests are asynchronous like request will go then server will process and then
  //response will come later and angular can't block execution waiting for response that's why angular uses observables
  //observable is a stream of data that arrives over time and we can think of it as Youtube Live stream i.e, data might arrive later or multiple times so observable waits for it
  getAllDevices(): Observable<DeviceResponse[]> {
    return this.http.get<DeviceResponse[]>(this.baseUrl);
  }

  getDeviceById(id: string): Observable<DeviceResponse> {
    return this.http.get<DeviceResponse>(`${this.baseUrl}/${id}`);
  }

  createDevice(device: DeviceRequest): Observable<DeviceResponse> {
    return this.http.post<DeviceResponse>(this.baseUrl, device);
  }

  updateDevice(id: string, device: DeviceRequest): Observable<DeviceResponse> {
    return this.http.put<DeviceResponse>(`${this.baseUrl}/${id}`, device);
  }

  deleteDevice(id: string): Observable<any> {
    //delete request often returns  204 i.e, No Content thats why i didnt mentioned any type so by default its <any>
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
//Note: one more thing is , in parameters we have changing interface like Device request in
//create/update and device response in getDevice but in all observable and post/put delete
// we have only Device response, Reason is when we are making a request then we are sending
//device object in 2 forms that's why difference is in parameters but we receive device object from backend only in DTO
//layer format that is Device Response that's why observable and post/put... methods have device response only
