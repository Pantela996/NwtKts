import { Component, OnInit } from '@angular/core';
import { LocationService } from 'src/app/services/location.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-location',
  templateUrl: './create-location.component.html',
  styleUrls: ['./create-location.component.css']
})
export class CreateLocationComponent implements OnInit {

  private location;

  constructor(private locationService: LocationService, public router: Router) {
    this.location = {
      name: '',
      locationCity: '',
      user_id: '',
      numberOfHalls: 1
    };
   }

  ngOnInit() {
  }

  createLocation() {

    this.locationService.createLocation(this.location).subscribe(success => {
      this.router.navigate(['/read_update_location']);
    }, err => {
      alert(err.error);
      console.log(err);
    });

  }


}
