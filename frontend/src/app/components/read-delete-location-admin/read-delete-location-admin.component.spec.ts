import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReadDeleteLocationAdminComponent } from './read-delete-location-admin.component';

describe('ReadDeleteLocationAdminComponent', () => {
  let component: ReadDeleteLocationAdminComponent;
  let fixture: ComponentFixture<ReadDeleteLocationAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReadDeleteLocationAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReadDeleteLocationAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
