import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ShelfService, ShelfSummary } from '../services/shelf.service';

@Component({
  selector: 'app-shelf-summary',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  styleUrls: ['./shelf-summary.css'],
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
        //this data is actually the response that i get back after making api call to backend from service file
        next: (data) => {
          this.shelf = data;
          this.cdr.detectChanges();
        },
        error: (err) => console.error(err)
      });
    }
  }

  editMode=false;
  enableEdit(){
    this.editMode= true;
    }

  updateShelf() {
    if (!this.shelf.id) return;
    //passed it as 'any' because update in backend expects only 2 fields partNumber and shelfName
    //but shelf here is summary one which has more properties so either create a new object with
    //2 property as shelfName and partNumber and then pass that object only or else pass it as any
    this.shelfService.updateShelf(this.shelf.id, this.shelf as any).subscribe({
      next: () => {
        this.editMode=false;
        this.cdr.detectChanges();
        }
    });
  }

  deleteShelf() {
    //this is non-null assertion as shelf id is marked as optional but delete shelf expects string so it cant be null so to ensure typescript it isnt null we put this exclamation
    if (!this.shelf.id) return;

    this.shelfService.deleteShelf(this.shelf.id).subscribe({
      next: () => this.router.navigate(['/shelves'])
    });
  }
}
