import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TicketReservationService } from './ticket-reservation.service';
import { RegularUserService } from './regular-user.service';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private readonly basePath = 'http://localhost:8081/event';

  ticketObject: any;
  constructor(private http: HttpClient, private userService: RegularUserService) {
    this.ticketObject = {event: null, rowsP: 0, columnsP: 0};
    this.http = http;
  }

  getAll() {
    return this.http.get(this.basePath + '/all', {responseType: 'json'});
  }

  getOne(eventId: string) {

    return this.http.get(this.basePath + '/one?' + 'event_id=' + eventId, {responseType: 'json'}).toPromise();
  }

  createHallSeats(seatingObj: any) {
    const headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log(seatingObj.seatsP);
    console.log('SEDISTA');

    return this.http.post(this.basePath + '/createEventHallMap', JSON.stringify(seatingObj), {headers, responseType: 'text'});
  }


  createEvent(event) {
    const headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(this.basePath + '/create', JSON.stringify(event), {headers, responseType: 'text'});
  }

  updateHallSeats(seatingObj: any, event= null) {
    const headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log(seatingObj.seatsP);
    console.log('SEDISTA');
    seatingObj.event = event;
    console.log(seatingObj.event);
    return this.http.post(this.basePath + '/update_seat_hall', JSON.stringify(seatingObj)  , {headers, responseType: 'text'});
  }

  completePayment(paymentId, payerId, token) {
    const headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json', Authorization: `Bearer` + token});
    return this.http.post(this.basePath + '/complete/payment?paymentId=' + paymentId + '&PayerID=' + payerId ,
     {headers, responseType: 'text'});
  }


  getImages(id: string) {
    return this.http.get(this.basePath + '/get-image?' + 'event_id=' + id, {responseType: 'json'});
  }
}

