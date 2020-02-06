import { Component, OnInit } from '@angular/core';
import { SuperUserService } from 'src/app/services/super-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-location-admin',
  templateUrl: './create-location-admin.component.html',
  styleUrls: ['./create-location-admin.component.css']
})
export class CreateLocationAdminComponent implements OnInit {

  private user;

  constructor(private superuserservice: SuperUserService, public router: Router) { 
    this.user = {
      firstname: "",
      lastname: "",
      username: "",
      password: "",
      email: ""
    }

  }

  ngOnInit() {
  }

  createLocationAdmin(){

    this.superuserservice.createLocationAdmin(this.user).subscribe(success =>{
      alert("Successfully");
      this.router.navigate(['/read_delete_location_admin'])
    }, err => {
      alert(err.error);
      console.log(err);
    }); 


    

  }

}
