import { Component, OnInit, Type, Output } from '@angular/core';
import { EventService } from 'src/app/services/event.service';
import { LocationService } from 'src/app/services/location.service';
import { Router } from '@angular/router';
import { SeatSelectionComponent } from '../seat-selection/seat-selection.component';
import { ThrowStmt } from '@angular/compiler';

import { Directive, Inject, ViewContainerRef } from '@angular/core';
import { TransferService } from 'src/app/services/transfer.service';
import { EventEmitter } from 'protractor';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit {

  private name: string;
  private locations;
  private numberOfHalls;
  private event;
  private locChosen = false;
  private halls;
  private halChosen = false;
  private hall;
  private dateFrom;
  private dateTo;
  private description;

  message: number;

  constructor(private data: TransferService, private locationService: LocationService,
              private eventService: EventService,
              public router: Router, private seatComponent: SeatSelectionComponent) {
    this.event = {
      name: '',
      dateFrom: '',
      dateTo: '',
      event_location: '',
      hall: null,
      event_category: '',
      description: '',

    };

   }

   ngOnInit() {
    this.data.currentMessage.subscribe(message => this.message = message);
    this.locationService.getAll().subscribe(success => {
      this.setLocations(success);
    });
  }

  setLocations(data) {
    if (data.length !== 0) {
      this.locations = data;
    }
  }

  setHalls(data) {
    if (data.length !== 0) {
      this.halls = data;
    }
  }

  locationChosen() {
    this.locChosen = true;
    this.numberOfHalls = this.event.event_location.numberOfHalls;
    this.locationService.getHalls(this.event.event_location.name).subscribe(success => {
      this.setHalls(success);
    });
  }

  hallChosen() {
    this.data.changeRows(this.event.location_hall.totalRows);
    this.data.changeColumns(this.event.location_hall.totalColumns);
    this.halChosen = false;
    setTimeout(() => {
       this.halChosen = true;
    });

    // this.seatComponent.initializeSeatMap(this.event.location_hall.totalRows,this.event.location_hall.totalColumns);
    // this.locationService.setRowsColumns(this.event.location_hall.totalRows, this.event.location_hall.totalColumns);
  }

  createEvent() {
    this.data.currentMessage3.subscribe(message => this.hall = message);
    console.log(this.hall);
    this.event.hall = this.hall;
    this.event.hall.id = this.event.location_hall.id;
    console.log(this.event);
    this.eventService.createEvent(this.event).subscribe(success => {
      this.router.navigate(['/']);
    });
  }

  isDateGreater() {
    const d1 = new Date(this.event.dateFrom);
    const d2 = new Date(this.event.dateTo);

    if (d2 > d1) {
      return true;
    } else {
      return false;
    }


  }

  array(n: number) {
    return Array(n);

  }


}
