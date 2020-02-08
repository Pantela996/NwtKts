import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReadUpdateLocationComponent } from './read-update-location.component';

describe('ReadUpdateLocationComponent', () => {
  let component: ReadUpdateLocationComponent;
  let fixture: ComponentFixture<ReadUpdateLocationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReadUpdateLocationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReadUpdateLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
