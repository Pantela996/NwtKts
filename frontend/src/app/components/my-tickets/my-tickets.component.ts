import { Component, OnInit } from '@angular/core';
import { RegularUserService } from 'src/app/services/regular-user.service';

@Component({
  selector: 'app-my-tickets',
  templateUrl: './my-tickets.component.html',
  styleUrls: ['./my-tickets.component.css']
})
export class MyTicketsComponent implements OnInit {

  private tickets;

  constructor(private userService: RegularUserService) { }

  ngOnInit() {
    this.userService.getMyTickets().subscribe(success => {
        this.setTickets(success);
    }, err => {
      alert(err.error);
      console.log(err);
    });
  }

  setTickets(data) {
    this.tickets = data;
    console.log(this.tickets);
  }

  buyTickets(ticket) {
    this.userService.buyTickets(ticket).subscribe(success => {
      this.setRedirect(success);
  }, err => {
    alert(err.error);
    console.log(err);
  });
  }

  setRedirect(data) {
    console.log(data);
    window.location.href = data.redirect_url;
  }

  isBefore(ticket) {
    const date = new Date();
    const ticketDate = new Date(ticket.event.dateTo);

    if (ticketDate < date) {
      return false;
    }

    return true;
    console.log(ticketDate);
    console.log(date);
  }

  onDelete(ticket) {
    this.userService.deleteTicket(ticket).subscribe(success => {
      this.removeTicket(success);
  }, err => {
    alert(err.error);
    console.log(err);
  });
  }

  removeTicket(data) {
    console.log(this.tickets.indexOf(data));
    this.tickets.splice(this.tickets.indexOf(data) + 1, 1);
  }
}
