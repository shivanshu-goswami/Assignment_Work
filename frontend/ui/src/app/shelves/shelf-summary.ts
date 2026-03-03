import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ShelfService, Shelf } from '../services/shelf.service';

@Component({
  selector: 'app-shelf-summary',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './shelf-summary.html'
})
export class ShelfSummary implements OnInit {

  shelf!: Shelf;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private shelfService: ShelfService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!;
    this.shelfService.getShelfById(id).subscribe({
      next: data => this.shelf = data
    });
  }

  updateShelf() {
    this.shelfService.updateShelf(this.shelf.id!, this.shelf).subscribe({
      next: () => alert('Shelf Updated Successfully')
    });
  }

  deleteShelf() {
    this.shelfService.deleteShelf(this.shelf.id!).subscribe({
      next: () => this.router.navigate(['/shelves'])
    });
  }
}
