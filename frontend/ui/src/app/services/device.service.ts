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

export interface Device {
  id: string;
  name: string;
  partNumber: string;
  building: string;
  type: string;
  shelfPositions: ShelfPosition[];
}

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private baseUrl = 'http://localhost:8080/devices';

  constructor(private http: HttpClient) {}

  getAllDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(this.baseUrl);
  }

  getDeviceById(id: string): Observable<Device> {
    return this.http.get<Device>(`${this.baseUrl}/${id}`);
  }

  createDevice(device: any): Observable<Device> {
    return this.http.post<Device>(this.baseUrl, device);
  }

  updateDevice(id: string, device: Device): Observable<Device> {
    return this.http.put<Device>(`${this.baseUrl}/${id}`, device);
  }

  deleteDevice(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
