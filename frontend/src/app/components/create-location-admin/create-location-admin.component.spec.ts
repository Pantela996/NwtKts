import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateLocationAdminComponent } from './create-location-admin.component';

describe('CreateLocationAdminComponent', () => {
  let component: CreateLocationAdminComponent;
  let fixture: ComponentFixture<CreateLocationAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateLocationAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateLocationAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
