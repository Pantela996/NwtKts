import { Component, OnInit } from '@angular/core';
import { SuperUserService } from 'src/app/services/super-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  private user;

  constructor(private superUserService: SuperUserService, private router: Router) {
    this.user = {
      username: '',
      password: '',
      email: '',
      name: '',
      lastName: '',
      date_of_creation: new Date(),
    };
   }

  ngOnInit() {
  }

  createUser() {

    this.superUserService.createUser(this.user).subscribe(success => {
      this.router.navigate(['/']);
    }, err => {
      alert(err.error);
      console.log(err);
    });



  }


}
