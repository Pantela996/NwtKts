import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private readonly basePath = 'http://localhost:8081/location';

  private hallRows;
  private hallColumns;

  constructor(private http: HttpClient) {
    this.http = http;
   }

   getAll(){
    return this.http.get(this.basePath + "/all", {responseType: 'json'})
  }
  getHalls(location:string){
    return this.http.get(this.basePath + '/halls/'+location, {responseType: 'json'})
  }


  createLocation(location){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post(this.basePath + "/create" ,JSON.stringify(location), {headers, responseType: 'text'});
  }

  updateLocation(name:string){
    console.log(name);

  }

  setRowsColumns(rows:number, columns:number){
    this.hallColumns = columns;
    this.hallRows = rows;

  }
  getRows(){
    return this.hallRows;
  }
  getColumns(){
    return this.hallColumns;
  }

}
