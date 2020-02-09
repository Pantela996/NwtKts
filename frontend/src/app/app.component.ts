import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './services/security/authentication-service.service';
import { Router, NavigationStart, NavigationEnd } from '@angular/router';
import {EventService} from './services/event.service';
import { TicketReservationService } from './services/ticket-reservation.service';
import * as $ from "jquery";
import { TransferService } from 'src/app/services/transfer.service';
import { ActivatedRoute } from '@angular/router'

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

  constructor(private activatedRoute: ActivatedRoute,private authService:AuthenticationService, public router:Router, private eventService:EventService, private transferService:TransferService) {
      this.activatedRoute.queryParams.subscribe(params => {
      let payerId = params['PayerID'];
      let paymentId = params['paymentId'];
      let token = params['token'];
      this.eventService.completePayment(paymentId, payerId,token).subscribe(success =>{
      }, err => {
        alert(err.error);
      });   
      console.log(payerId); // Print the parameter to the console. 
      console.log(paymentId);
  });
   }

  ngOnInit() {
    console.log('asdas')
    this.eventService.getAll().subscribe(success => {this.setEvents(success)});
    this.slide_count = 0;

    this.router.events.subscribe(event => {
      if(event instanceof NavigationEnd && this.router.url === '/') {
        this.eventService.getAll().subscribe(success => {this.setEvents(success)});
        }
      })
  }


  setImages(data){
    console.log(data);
    for(let i = 0;i < data.length;i++){
      this.image_slider.push(data[i]);
    }
    console.log(this.image_slider);
    if(this.image_slider.length > 0){
      $('#itemPreview').attr('src', `data:image/png;base64,${this.image_slider[0]}`);
    }
   
    
  }


  setEvents(data){
    console.log(data)
    if(data.length != 0){
      this.events = data;
      console.log(this.events);
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

    return (this.router.url === '/' || this.router.url.startsWith('/?paymentId')); 
  }

  info(id){
    var information  = document.getElementById("information");
    //, err => alert(err.message)
    this.eventService.getImages(id).subscribe(success=>{this.setImages(success)});
    if(this.image_slider.length > 0){
      $(function(){ 
        var $information = $("#information");
          if($('.more-info').css("display") == "none"){
            $('.more-info').fadeIn('slow');
          }else{
            $('.more-info').fadeOut('slow');
          };
          
    });
    }
   
  
  }

 reservation(event){
  //this.transferService.setCurrent(event);
  this.router.navigate(['/reservation', event.id]);
 }
 
 ngOnDestroy() {
  alert("HEREEEEEE");

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

 showLocations(){
  this.router.navigate(['/read_delete_location']);
 }

 showAllUsers(){
  this.router.navigate(['/read_delete_users']);
 }
 createLocations(){
   this.router.navigate(['/read_update_location'])
 }
 createEvent(){
   this.router.navigate(['/create_event'])
 }

 isRegularUser(){
  var user = this.authService.getCurrentUser();
  var roles = this.authService.getRoles();
  if(roles.includes("REGULAR_USER_ROLE")){
    return true;
  }else{
    return false;
  }
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

  getMyCartItems(){
    
  }


}

