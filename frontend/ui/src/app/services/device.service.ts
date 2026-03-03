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

export interface DeviceResponse {
  id: string;
  name: string;
  partNumber: string;
  building: string;
  type: string;
  shelfPositions: ShelfPosition[];
}

export interface DeviceRequest {
  id?: string;
  deviceName: string;
  partNumber: string;
  buildingName: string;
  deviceType: string;
  numberOfShelfPositions: number;
}


@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private baseUrl = 'http://localhost:8080/devices';

  constructor(private http: HttpClient) {}

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
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
