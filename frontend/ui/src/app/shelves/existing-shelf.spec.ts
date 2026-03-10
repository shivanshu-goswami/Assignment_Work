import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExistingShelf } from './existing-shelf';

describe('ExistingShelf', () => {
  let component: ExistingShelf;
  let fixture: ComponentFixture<ExistingShelf>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExistingShelf]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExistingShelf);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
