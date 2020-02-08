import { Component, OnInit, Type } from '@angular/core';
import { EventService } from 'src/app/services/event.service'
import { LocationService } from 'src/app/services/location.service';
import { Router } from '@angular/router';
import { SeatSelectionComponent } from '../seat-selection/seat-selection.component';
import { ThrowStmt } from '@angular/compiler';

import { Directive, Inject, ViewContainerRef } from '@angular/core';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit {

  private locations;
  private numberOfHalls;
  private event;
  private locChosen = false;
  private halls;
  private halChosen = false;


  constructor(private locationService: LocationService, private eventService: EventService, public router:Router, private seatComponent:SeatSelectionComponent) {
    this.event = {
      name: "",
      date_from: "",
      date_to: "",
      event_location: "",
      location_hall: "",
      event_category: "",
      description: "",

    }

   }

   ngOnInit() {
    this.locationService.getAll().subscribe(success => {
      this.setLocations(success)
    });
  }

  setLocations(data){
    if(data.length != 0){
      this.locations = data;
    }
  }

  setHalls(data){
    if(data.length != 0){
      this.halls = data;
    }
  }

  locationChosen(){
    this.locChosen = true;
    this.numberOfHalls = this.event.event_location.numberOfHalls;
    this.locationService.getHalls(this.event.event_location.name).subscribe(success =>{
      this.setHalls(success)
    });
  }

  hallChosen(){
    this.halChosen = true;
    this.seatComponent.processBooking(this.event.location_hall.totalRows,this.event.location_hall.totalColumns)
    //this.locationService.setRowsColumns(this.event.location_hall.totalRows, this.event.location_hall.totalColumns);
  }
    

  array(n:number){
    return Array(n);

  }


}
