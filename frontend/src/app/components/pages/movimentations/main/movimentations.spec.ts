import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Movimentations } from './movimentations';

describe('Movimentations', () => {
  let component: Movimentations;
  let fixture: ComponentFixture<Movimentations>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Movimentations]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Movimentations);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
