import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ShelfService, ShelfSummary } from '../services/shelf.service';

@Component({
  selector: 'app-shelf-summary',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './shelf-summary.html'
})
export class ShelfSummaryComponent implements OnInit {

  shelf!: ShelfSummary;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private shelfService: ShelfService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.shelfService.getShelfSummary(id).subscribe({
        next: (data) => {
          this.shelf = data;
          this.cdr.detectChanges();
        },
        error: (err) => console.error(err)
      });
    }
  }

  updateShelf() {
    if (!this.shelf.id) return;

    this.shelfService.updateShelf(this.shelf.id, this.shelf as any).subscribe({
      next: () => alert('Shelf Updated Successfully')
    });
  }

  deleteShelf() {
    if (!this.shelf.id) return;

    this.shelfService.deleteShelf(this.shelf.id).subscribe({
      next: () => this.router.navigate(['/shelves'])
    });
  }
}
