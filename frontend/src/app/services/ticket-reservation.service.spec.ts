import { TestBed } from '@angular/core/testing';

import { TicketReservationService } from './ticket-reservation.service';

describe('TicketReservationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TicketReservationService = TestBed.get(TicketReservationService);
    expect(service).toBeTruthy();
  });
});
