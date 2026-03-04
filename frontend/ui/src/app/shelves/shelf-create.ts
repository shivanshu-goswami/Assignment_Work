import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ShelfService, Shelf } from '../services/shelf.service';

@Component({
  selector: 'app-shelf-create',
  standalone: true,
  imports: [FormsModule, RouterLink],
  styleUrls: ['./shelf-create.css'],
  templateUrl: './shelf-create.html'
})
export class ShelfCreate implements OnInit {

  shelf: Shelf = {
    shelfName: '',
    partNumber: ''
  };

  shelfPositionId!: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private shelfService: ShelfService
  ) {}

  ngOnInit(): void {
    this.shelfPositionId =
      this.route.snapshot.queryParamMap.get('shelfPositionId')!;
  }

  createShelf() {
    this.shelfService
      .createShelf(this.shelfPositionId, this.shelf)
      .subscribe({
        next: () => this.router.navigate(['/devices'])
      });
  }
}
