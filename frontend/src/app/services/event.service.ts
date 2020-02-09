import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TicketReservationService } from './ticket-reservation.service';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private readonly basePath = 'http://localhost:8081/event';
  
  ticketObject:any;
  constructor(private http: HttpClient) {
    this.ticketObject = {event:null,rowsP:0,columnsP:0}; 
    this.http = http;
  }

  getAll(){
    return this.http.get(this.basePath + "/all", {responseType: 'json'})
  }

  getOne(event_id:String){

    return this.http.get(this.basePath + "/one?" + "event_id=" + event_id, {responseType: 'json'}).toPromise();
  }  

  createHallSeats(seatingObj:any){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log(seatingObj.seatsP);
    console.log("SEDISTA");
    
    return this.http.post(this.basePath + "/createEventHallMap",JSON.stringify(seatingObj), {headers, responseType: 'text'});
  }


  createEvent(event){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(this.basePath + "/create",JSON.stringify(event), {headers, responseType: 'text'});
  }

  updateHallSeats(seatingObj:any, event=null){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log(seatingObj.seatsP);
    console.log("SEDISTA");
    seatingObj.event = event;
    console.log(seatingObj.event);
    return this.http.post(this.basePath+'/make/payment',JSON.stringify(seatingObj)  ,{headers, responseType: 'json'});
  }

  completePayment(paymentId, payerId,token) {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json','Authorization': `Bearer`+token});
    return this.http.post(this.basePath + '/complete/payment?paymentId=' + paymentId + '&PayerID=' + payerId , {headers, responseType: 'text'})
  }


  getImages(id:String){
    return this.http.get(this.basePath + "/get-images?" + "event_id=" + id, {responseType: 'json'});
  }
}

