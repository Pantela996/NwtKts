import { Component, OnInit } from '@angular/core';
import { LocationService } from 'src/app/services/location.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-read-update-location',
  templateUrl: './read-update-location.component.html',
  styleUrls: ['./read-update-location.component.css']
})
export class ReadUpdateLocationComponent implements OnInit {

  public locations = [];

  constructor(private locationService: LocationService, private router: Router) { }

  ngOnInit() {
    this.locationService.getAll().subscribe(success => {
      this.setLocations(success);
    });
  }

  setLocations(data) {
    if (data.length !== 0) {
      this.locations = data;

    }
  }

  addNewLocation() {
    this.router.navigate(['/create_location']);
  }

  onUpdate(name: string) {
    // this.locationService.updateLocation(name).subscribe();

  }

}
