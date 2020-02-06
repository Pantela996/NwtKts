import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReadDeleteUsersComponent } from './read-delete-users.component';

describe('ReadDeleteUsersComponent', () => {
  let component: ReadDeleteUsersComponent;
  let fixture: ComponentFixture<ReadDeleteUsersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReadDeleteUsersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReadDeleteUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
