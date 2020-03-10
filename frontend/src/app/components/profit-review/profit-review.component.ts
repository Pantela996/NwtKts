import { Component, OnInit } from '@angular/core';
import { SuperUserService } from '../../services/super-user.service';
import { EventService } from 'src/app/services/event.service';
import { LocationService } from 'src/app/services/location.service';
import { AuthenticationService } from '../../services/security/authentication-service.service';

@Component({
  selector: 'app-profit-review',
  templateUrl: './profit-review.component.html',
  styleUrls: ['./profit-review.component.css']
})
export class ProfitReviewComponent implements OnInit {

  constructor( private superUserService: SuperUserService, private eventService: EventService, private locationService: LocationService,
               private authService: AuthenticationService) { }

  private regularUsers;
  private totalProfit = 0;
  private tickets = [];
  private locations = [];
  private locationName = '';


  ngOnInit() {
    this.superUserService.getRegularAllUsers().subscribe(success => {this.setRegularUsers(success); });
    this.locationService.getAll().subscribe(success => {this.setLocation(success); } );

  }

  setLocation(data) {
    this.locations = data;
  }

  onEditClick(value) {
    this.tickets = [];
    this.totalProfit = 0;
    const locations = [];
    for (let i = 0; i < this.regularUsers.length; i++) {
      for (let j = 0; j < this.regularUsers[i].ticket.length; j++) {
        if (this.regularUsers[i].ticket[j].is_payed === true) {
          if (this.regularUsers[i].ticket[j] != null) {
            if (this.regularUsers[i].ticket[j].event.location.name === value) {
              this.tickets.push(this.regularUsers[i].ticket[j]);
              this.totalProfit += this.regularUsers[i].ticket[j].price;
            }
          }



        }

      }
    }

  }

isLocationEventAdmin() {

    const user = this.authService.getCurrentUser();
    const  roles = this.authService.getRoles();
    if (roles.includes('LOCATION_AND_EVENT_ADMIN_ROLE')) {
     return true;
    } else {
     return false;
    }
  }

  setRegularUsers(data) {
    this.regularUsers = data;
    this.getProfit(this.regularUsers);
    console.log(this.totalProfit);
    console.log(this.tickets);
  }


  getProfit(users) {
    console.log(users);
    let totalProfit = 0;
    for (let i = 0; i < users.length; i++) {
      for (let j = 0; j < users[i].ticket.length; j++) {
        if (users[i].ticket[j].is_payed === true) {
          this.tickets.push(users[i].ticket[j]);
          this.totalProfit += users[i].ticket[j].price;
        }

      }
    }
    return totalProfit;
  }

}
