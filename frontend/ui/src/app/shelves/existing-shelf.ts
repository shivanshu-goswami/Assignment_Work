import { Component,OnInit,ChangeDetectorRef } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import { ShelfService, Shelf } from '../services/shelf.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-existing-shelf',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './existing-shelf.html',
  styleUrl: './existing-shelf.css',
})
export class ExistingShelf implements OnInit{
  shelves!:Shelf[];
  shelfPositionId!:string;

  constructor(
    private route: ActivatedRoute,
    private shelfService: ShelfService,
    private cdr: ChangeDetectorRef,
    private router: Router
    ){}

 ngOnInit():void{
   this.shelfPositionId = this.route.snapshot.queryParamMap.get('shelfPositionId')!;
   this.shelfService.getUnattachedShelves().subscribe((data)=>{
     this.shelves=data;
      this.cdr.detectChanges();
     })
   }

   addShelfToSp(shelfId:string){
     this.shelfService.attachExistingShelf(shelfId,this.shelfPositionId).subscribe(()=>{
       this.router.navigate(['/devices'])
       })
     }
}
