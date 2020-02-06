import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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

  createHallSeats(seatingObj:any){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log(seatingObj.seatsP);
    console.log(seatingObj.rowsP);
    console.log(seatingObj.columnsP);
    return this.http.post(this.basePath + "/createEventHallMap",JSON.stringify(seatingObj), {headers, responseType: 'text'}).toPromise();
  }


  getImages(id:String){
    return this.http.get(this.basePath + "/get-images?" + "event_id=" + id, {responseType: 'json'});
  }
}

