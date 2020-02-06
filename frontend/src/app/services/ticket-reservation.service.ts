import { Injectable, OnInit, Output, EventEmitter } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventService } from './event.service';


@Injectable({
  providedIn: 'root'
})
export class TicketReservationService {

  public  event;


  constructor(private http: HttpClient, private eventService:EventService) {
    this.http = http;
   }



  setEvent(data){
    console.log(data);
    this.event = data;
  }

  getCurrent(){
    return this.event.asObservable();
  }
}
