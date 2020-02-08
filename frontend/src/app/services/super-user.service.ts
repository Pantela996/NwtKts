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

  createUser(createdUser){
    var headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    console.log(createdUser.username);
    console.log(createdUser.password);
    console.log(createdUser.email);
    console.log(createdUser.name);
    console.log(createdUser.lastName);
    console.log(createdUser.date_of_creation);

    return this.http.post(this.basePath + '/register', JSON.stringify(createdUser), {headers, responseType: 'text'})
   
  }

  getAll(){
    return this.http.get(this.basePath + "/get_all_event_admins", {responseType: 'json'})
  }
  

  getAllUsers(){
    return this.http.get(this.basePath + "/get_all_users", {responseType: 'json'})
  }

  getAllLocations(){
    return this.http.get(this.basePath + "/location/all", {responseType: 'json'})
  }

  deleteUser(username:string){
    return this.http.delete(this.basePath + "/delete_location_admin/" + username, {responseType: 'text'})
  }

  deleteRegularUser(username:string){
    return this.http.delete(this.basePath + "/delete_user/" + username, {responseType: 'text'})
  }

  deleteLocation(id:string){
    console.log("tu sam")
    return this.http.delete(this.basePath + "/delete_location/"+id, {responseType: 'text'})
  }



}
