import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './services/security/authentication-service.service';
import { Router, NavigationStart, NavigationEnd } from '@angular/router';
import {EventService} from './services/event.service';
import { TicketReservationService } from './services/ticket-reservation.service';
import * as $ from 'jquery';
import { TransferService } from 'src/app/services/transfer.service';
import { ActivatedRoute } from '@angular/router';
import { SuperUserService } from './services/super-user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {


  public events = [];
  title = 'frontend';
  firstVisit = true;
  imgSrc = '';
  imageSlider = [];
  slideCount = 0;
  private regularUsers;
  searchText = '';

  constructor(private activatedRoute: ActivatedRoute, private authService: AuthenticationService, public router: Router,
              private eventService: EventService, private transferService: TransferService,
              private superUserService: SuperUserService) {
      this.activatedRoute.queryParams.subscribe(params => {
      const payerId = params.PayerID;
      const paymentId = params.paymentId;
      const token = params.token;
      this.eventService.completePayment(paymentId, payerId, token).subscribe(success => {
      }, err => {
        alert(err.error);
      });
      console.log(payerId); // Print the parameter to the console.
      console.log(paymentId);
  });
   }

  ngOnInit() {
    console.log('asdas');
    this.eventService.getAll().subscribe(success => {this.setEvents(success); });
    this.slideCount = 0;

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd && this.router.url === '/') {
        this.eventService.getAll().subscribe(success => {this.setEvents(success); });
        }
      });
  }



  applyFilter(filterValue: string) {
    alert('here');
    // this.events.filter = filterValue.trim().toLowerCase();
  }

  searchBox(value) {
    if (value === '') {
      this.eventService.getAll().subscribe(success => {this.setEvents(success); });
    }
    this.events = this.events
    .filter(event => event.name.includes(value));
  }

  setImages(data) {
    console.log(data);
    for (let i = 0; i < data.length; i++) {
      this.imageSlider.push(data[i]);
    }
    console.log(this.imageSlider);
    if (this.imageSlider.length > 0) {
      $('#itemPreview').attr('src', `data:image/png;base64,${this.imageSlider[0]}`);
    }


  }


  setEvents(data) {
    console.log(data);
    if (data.length !== 0) {
      this.events = data;
      console.log(this.events);
      console.log(this.events[0].locationId);
    }

  }
  loggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  creatSeatMap() {
    this.router.navigate(['/seatmap']);
  }

  isMainPageRoute() {

    return (this.router.url === '/' || this.router.url.startsWith('/?paymentId'));
  }

  info(id) {
    const information  = document.getElementById('information');
    // , err => alert(err.message)
    this.eventService.getImages(id).subscribe(success => {this.setImages(success); });
    if (this.imageSlider.length > 0) {
      this.imageSlider  = [];
      $(function() {
        const $information = $('#information');
        if ($('.more-info').css('display') === 'none') {
            $('.more-info').fadeIn('slow');
          } else {
            $('.more-info').fadeOut('slow');
          }

    });
    }


  }

 reservation(event) {
  // this.transferService.setCurrent(event);
  this.router.navigate(['/reservation', event.id]);
 }



isBefore(date) {
  const current = new Date();

  date = new Date(date);
  date.setDate(date.getDate() - 3);

  console.log(current, date);
  if (date < current) {
    return false;
  }

  return true;


}

profitReview() {
  this.router.navigate(['/profit_review']);

}


 isSuperAdmin() {
   const user = this.authService.getCurrentUser();
   const roles = this.authService.getRoles();
   if (roles.includes('ADMIN_ROLE')) {
     return true;
   } else {
     return false;
   }
 }
 showEventAdmins() {
  this.router.navigate(['/read_delete_location_admin']);
 }

 showLocations() {
  this.router.navigate(['/read_delete_location']);
 }

 showAllUsers() {
  this.router.navigate(['/read_delete_users']);
 }
 createLocations() {
   this.router.navigate(['/read_update_location']);
 }
 createEvent() {
   this.router.navigate(['/create_event']);
 }

 isRegularUser() {
  const user = this.authService.getCurrentUser();
  const roles = this.authService.getRoles();
  if (roles.includes('REGULAR_USER_ROLE')) {
    return true;
  } else {
    return false;
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
  next_slide(val: number) {
    if (val > 0) {
      if (this.slideCount + val === this.imageSlider.length) {
        this.imgSrc = this.imageSlider[0];
        this.slideCount = 0;

      } else {
        this.slideCount++;
        this.imgSrc = this.imageSlider[this.slideCount];
      }
    } else {
      if (this.slideCount + val < 0) {
        this.slideCount = this.imageSlider.length - 1;
        this.imgSrc = this.imageSlider[this.slideCount];
      } else {
        this.slideCount--;
        this.imgSrc = this.imageSlider[this.slideCount];
      }
    }
    $('#itemPreview').attr('src', `data:image/png;base64,${this.imgSrc}`);

  }

  getMyCartItems() {
    this.router.navigate(['/my_tickets']);
  }


}

