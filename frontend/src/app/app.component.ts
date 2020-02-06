import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './services/security/authentication-service.service';
import { Router } from '@angular/router';
import {EventService} from './services/event.service';
import { TicketReservationService } from './services/ticket-reservation.service';
import * as $ from "jquery";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  

  public events = []
  title = 'frontend';
  first_visit = true;
  img_src = "";
  image_slider = [];
  slide_count = 0;

  constructor(private authService:AuthenticationService, public router:Router, private eventService:EventService, private ticketService:TicketReservationService) { }

  ngOnInit() {
    this.eventService.getAll().subscribe(success => {this.setEvents(success)});
    this.slide_count = 0;
  }


  setImages(data){
    console.log(data);
    for(let i = 0;i < data.length;i++){
      this.image_slider.push(data[i]);
    }
    console.log(this.image_slider);
    $('#itemPreview').attr('src', `data:image/png;base64,${this.image_slider[0]}`);
    
  }


  async setEvents(data){
    
    if(data.length != 0){
      this.events = data;
      console.log(this.events[0].locationId);
    }

  }
  loggedIn():boolean{
    return this.authService.isLoggedIn();
  }

  login():void{
    this.router.navigate(['/login']);
  }

  logout(){
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  creatSeatMap(){
    this.router.navigate(['/seatmap']);
  }

  isMainPageRoute(){
    return (this.router.url === '/'); 
  }

  info(id){
    var information  = document.getElementById("information");
    this.eventService.getImages(id).subscribe(success=>{this.setImages(success)});
    $(function(){ 
      var $information = $("#information");
        if($('.more-info').css("display") == "none"){
          $('.more-info').fadeIn('slow');
        }else{
          $('.more-info').fadeOut('slow');
        };
        
  });
  
  }

 reservation(event){
  this.ticketService.setCurrent(event);
  this.router.navigate(['/reservation']);

 } 

 isSuperAdmin(){
   var user = this.authService.getCurrentUser();
   var roles = this.authService.getRoles();
   if(roles.includes("ADMIN_ROLE")){
     return true;
   }else{
     return false;
   }
 }
 showEventAdmins(){
  this.router.navigate(['/read_delete_location_admin']);
 }

 isLocationEventAdmin(){
  
    var user = this.authService.getCurrentUser();
    var  roles = this.authService.getRoles();
    if(roles.includes("LOCATION_AND_EVENT_ADMIN_ROLE")){
     return true;
    }else{
     return false;
    }
  }
  next_slide(val:number){
    if(val > 0){
      if(this.slide_count + val == this.image_slider.length){
        this.img_src = this.image_slider[0];
        this.slide_count = 0;
        ;
      }else{
        this.slide_count++;
        this.img_src = this.image_slider[this.slide_count];
      }
    }else{
      if(this.slide_count + val < 0){
        this.slide_count = this.image_slider.length - 1;
        this.img_src = this.image_slider[this.slide_count];
      }else{
        this.slide_count--;
        this.img_src = this.image_slider[this.slide_count];
      }
    }
    $('#itemPreview').attr('src', `data:image/png;base64,${this.img_src}`);

  }


}

