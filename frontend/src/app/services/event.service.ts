import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TicketReservationService } from './ticket-reservation.service';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private readonly basePath = 'http://localhost:8081/event';

  constructor(private http: HttpClient) { 
    this.http = http;
  }

  getAll(){
    return this.http.get(this.basePath + "/all", {responseType: 'json'})
  }

  getOne(event_id:String){
    return this.http.get(this.basePath + "/one?" + "event_id=1", {responseType: 'json'}).toPromise();
  }  

  createHallSeats(seatingObj:any){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log(seatingObj.seatsP);
    console.log("SEDISTA");
    
    return this.http.post(this.basePath + "/createEventHallMap",JSON.stringify(seatingObj), {headers, responseType: 'text'});
  }


  getImages(id:String){
    return this.http.get(this.basePath + "/get-images?" + "event_id=" + id, {responseType: 'json'});
  }
}

