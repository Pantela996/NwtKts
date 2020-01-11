import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/security/authentication-service.service';
import { EventEmitter,Output } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public user;

  public wrongUsernameOrPass:boolean;

  @Output()
  changeDisplay:EventEmitter<any> = new EventEmitter();

  constructor(private authenticationService:AuthenticationService,
              private router: Router) {
    this.user = {};

    this.wrongUsernameOrPass = false;
   }

  ngOnInit() {
   
  }

  login():void{
    this.authenticationService.login(this.user.name, this.user.password,this);
  }

  handleLogin(loggedIn){
    if(loggedIn){
      console.log("SUCCESSFUL COMPONENT");
      this.router.navigate(['/']);          
    }else{
      this.wrongUsernameOrPass = true;
      console.log("ERROR COMPONENT");
    }
  }



}
