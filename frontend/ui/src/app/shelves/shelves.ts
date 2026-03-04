import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ShelfService, Shelf } from '../services/shelf.service';

@Component({
  selector: 'app-shelves',
  standalone: true,
  imports: [CommonModule, RouterLink],
  styleUrls: ['./shelves.css'],
  templateUrl: './shelves.html'
})
export class Shelves implements OnInit {

  shelves: Shelf[] = [];

  constructor(
    private shelfService: ShelfService,
    private cdr: ChangeDetectorRef
    ) {}

  ngOnInit(): void {
    this.loadShelves();
  }

  loadShelves() {
      this.shelfService.getAllShelves().subscribe(data => {
        this.shelves = data;
        this.cdr.detectChanges(); // important
      });
    }

  deleteShelf(id: string) {
    this.shelfService.deleteShelf(id).subscribe({
      next: () => this.loadShelves()
    });
  }
}
