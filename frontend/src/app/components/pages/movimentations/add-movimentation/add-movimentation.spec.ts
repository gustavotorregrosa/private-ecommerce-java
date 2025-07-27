import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMovimentation } from './add-movimentation';

describe('AddMovimentation', () => {
  let component: AddMovimentation;
  let fixture: ComponentFixture<AddMovimentation>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddMovimentation]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddMovimentation);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
