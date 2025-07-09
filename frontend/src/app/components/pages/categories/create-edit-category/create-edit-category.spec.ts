import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditCategory } from './create-edit-category';

describe('CreateEditCategory', () => {
  let component: CreateEditCategory;
  let fixture: ComponentFixture<CreateEditCategory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateEditCategory]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateEditCategory);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
