import { TestBed } from '@angular/core/testing';

import { RegularUserService } from './regular-user.service';

describe('RegularUserService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RegularUserService = TestBed.get(RegularUserService);
    expect(service).toBeTruthy();
  });
});
