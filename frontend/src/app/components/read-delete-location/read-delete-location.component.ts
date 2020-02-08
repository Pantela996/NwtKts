import { Component, OnInit } from '@angular/core';
import {SuperUserService} from "src/app/services/super-user.service"
import { Router } from '@angular/router';

@Component({
  selector: 'app-read-delete-location',
  templateUrl: './read-delete-location.component.html',
  styleUrls: ['./read-delete-location.component.css']
})
export class ReadDeleteLocationComponent implements OnInit {

  public locations = []

  constructor(private superuserservice: SuperUserService, private router:Router) { }

  ngOnInit() {
    this.superuserservice.getAllLocations().subscribe(success => {this.setLocations(success)});
  }

  setLocations(data){
    if(data.length != 0){
      this.locations = data;
      console.log(this.locations[0].name);

    }
  }
  onDelete(id:string){
    this.locations = this.locations.filter(u => u.id !== id);
    this.superuserservice.deleteLocation(id).subscribe();
  }


  
}
