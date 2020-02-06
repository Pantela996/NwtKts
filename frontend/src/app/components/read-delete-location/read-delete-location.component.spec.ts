import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReadDeleteLocationComponent } from './read-delete-location.component';

describe('ReadDeleteLocationComponent', () => {
  let component: ReadDeleteLocationComponent;
  let fixture: ComponentFixture<ReadDeleteLocationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReadDeleteLocationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReadDeleteLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
