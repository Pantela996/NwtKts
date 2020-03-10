import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfitReviewComponent } from './profit-review.component';

describe('ProfitReviewComponent', () => {
  let component: ProfitReviewComponent;
  let fixture: ComponentFixture<ProfitReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfitReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfitReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
