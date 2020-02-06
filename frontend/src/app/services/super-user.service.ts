import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class SuperUserService {

  private readonly basePath = 'http://localhost:8081';

  constructor(private http: HttpClient) { 
    this.http = http;
  }

  createLocationAdmin(createdLocationAdmin){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log("Stigao sam u servis")
    console.log(createdLocationAdmin.seatsP);
    console.log(createdLocationAdmin.firstname);
    console.log(createdLocationAdmin.lastname);
    console.log(createdLocationAdmin.email);
    console.log(createdLocationAdmin.username);
    console.log(createdLocationAdmin.password);

    return this.http.post(this.basePath + "/create_location_admin",JSON.stringify(createdLocationAdmin), {headers, responseType: 'text'});

  }

  getAll(){
    return this.http.get(this.basePath + "/get_all_event_admins", {responseType: 'json'})

  }

  deleteUser(username:string){
    return this.http.delete(this.basePath + "/delete_location_admin/" + username, {responseType: 'text'})

  }

}
