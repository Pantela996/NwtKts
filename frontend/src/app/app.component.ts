import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './services/security/authentication-service.service';
import { Router } from '@angular/router';
import {EventService} from './services/event.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  public events = []
  title = 'frontend';

  constructor(private authService:AuthenticationService, public router:Router, private eventService:EventService) { }

  ngOnInit() {
    this.eventService.getAll().subscribe(success => {this.setEvents(success)});
  }


  setEvents(data){
    
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

  info(){
    console.log("info");
  }

 isLocationEventAdmin(){
   var  roles = this.authService.getRoles();
   var user = this.authService.getCurrentUser();
   if(roles.includes("LOCATION_AND_EVENT_ADMIN_ROLE")){
    return true;
   }else{
    return false;
   }
  
 }


}
