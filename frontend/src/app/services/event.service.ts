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

}
