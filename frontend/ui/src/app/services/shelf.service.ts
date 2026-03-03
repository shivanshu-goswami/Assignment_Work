import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Shelf {
  id?: string;
  shelfName: string;
  partNumber: string;
  shelfPositionId?: string;
  deviceName?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ShelfService {

  private baseUrl = 'http://localhost:8080/shelves';

  constructor(private http: HttpClient) {}

  getAllShelves(): Observable<Shelf[]> {
    return this.http.get<Shelf[]>(this.baseUrl);
  }

  getShelfById(id: string): Observable<Shelf> {
    return this.http.get<Shelf>(`${this.baseUrl}/${id}`);
  }

  createShelf(shelfPositionId: string, shelf: Shelf): Observable<Shelf> {
    return this.http.post<Shelf>(`${this.baseUrl}/${shelfPositionId}`, shelf);
  }

  updateShelf(id: string, shelf: Shelf): Observable<Shelf> {
    return this.http.put<Shelf>(`${this.baseUrl}/${id}`, shelf);
  }

  deleteShelf(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
