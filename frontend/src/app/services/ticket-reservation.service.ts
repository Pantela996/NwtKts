import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TicketReservationService {

  private event;

  constructor() { }

  setCurrent(event){
    this.event = event;
  }

  getCurrent(){
    return this.event;
  }
}
