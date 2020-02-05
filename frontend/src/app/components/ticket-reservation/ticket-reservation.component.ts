import { Component, OnInit } from '@angular/core';
import { TicketReservationService } from 'src/app/services/ticket-reservation.service';

@Component({
  selector: 'app-ticket-reservation',
  templateUrl: './ticket-reservation.component.html',
  styleUrls: ['./ticket-reservation.component.css']
})
export class TicketReservationComponent implements OnInit {

  constructor(private ticketService:TicketReservationService) { }

  private event;

  ngOnInit() {
    this.event = this.ticketService.getCurrent();
    console.log(this.event);
  }

}
