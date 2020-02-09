import { Injectable } from '@angular/core';

import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransferService {

  private messageSource = new BehaviorSubject(0);
  private messageSource2 = new BehaviorSubject(0);
  private messageSource3 = new BehaviorSubject({seatsP:[],rowsP:0,columnsP:0});
  currentMessage = this.messageSource.asObservable();
  currentMessage2 = this.messageSource2.asObservable();
  currentMessage3 = this.messageSource3.asObservable();

  private seatingObject;

  constructor() { 
    this.seatingObject = {seatsP:[],rowsP:0,columnsP:0};
  }

  changeRows(message: number) {
    this.messageSource.next(message)
  }

  changeColumns(message: number) {
    this.messageSource2.next(message)
  }


  setEvent(message:any){
    this.messageSource3.next(message)
  }

}
