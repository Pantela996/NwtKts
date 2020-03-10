import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { AuthenticationService } from '../services/security/authentication-service.service';

@Injectable({
  providedIn: 'root'
})
export class RegularUserService {

  private readonly basePath = 'http://localhost:8081';


  constructor(private http: HttpClient, private authService: AuthenticationService) {
    this.http = http;
  }

   getMyTickets() {
    return this.http.get(this.basePath + '/my_tickets', {responseType: 'json'});
   }

   buyTickets(ticket) {
    const headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    console.log(ticket);

    return this.http.post(this.basePath + '/event/make/payment', JSON.stringify(ticket), {headers, responseType: 'json'});
   }

   deleteTicket(ticket) {
    const headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    const user = this.authService.getCurrentUser();
    console.log(user);
    return this.http.post(this.basePath + '/deleted', JSON.stringify(ticket), {headers, responseType: 'json'});
   }
}
