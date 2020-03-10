import { Component, OnInit, HostListener, ElementRef } from '@angular/core';
import { Inject, Input } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { Router } from '@angular/router';
import { EventService } from 'src/app/services/event.service';
import { TicketReservationService } from 'src/app/services/ticket-reservation.service';
import { LocationService } from 'src/app/services/location.service';
import { TransferService } from 'src/app/services/transfer.service';



@Component({
  selector: 'app-seat-selection',
  templateUrl: './seat-selection.component.html',
  styleUrls: ['./seat-selection.component.css']
})
export class SeatSelectionComponent implements OnInit {

  constructor(private data: TransferService, @Inject(DOCUMENT) document,
              eventService: EventService, public router: Router,
              private ticketService: TicketReservationService, private locationService: LocationService) {
    this.eventService  = eventService;
    this.seatingObject = {seatsP: [], rowsP: 0, columnsP: 0, seatLabel: '', seatNo : ''};
    this.data.currentMessage.subscribe(message => this.rows = message);
    this.data.currentMessage2.subscribe(message => this.columns = message);
   }




  public eventService: any;
  public start = true;
  private seatConfig: any = null;
  private seatmap = [];
  public isEntered = false;
  public forBuying = false;
  public rows: number;
  public columns: number;
  public status: string;
  public mode = 0;
  private seats = [];
  public selectedSeat = [];
  public passlineseats = [];
  public seatingObject: any;
  public seatsP;
  public rowsP;
  public columnsP;
  private eventId;
  private availableSeats = [];
  public selectedDocuments = [];
  private dragStart = 0;
  private dragOver = 0;
  private seatConf: any;
  private seatChartConfig = {
    showRowsLabel : false,
    showRowWisePricing : false,
    newSeatNoForRow : false
  };

  private cart = {
    selectedSeats : [],
    seatstoStore : [],
    totalamount : 0,
    cartId : '',
    eventId : 0
  };

  message: number;


  @HostListener('document:mousedown', ['$event'])
  onMouseDown(ev) {
      this.dragStart = ev.clientY;
  }
  @HostListener('document:mouseup', ['$event'])
  onMouseUp(ev) {
      this.dragStart = 0;
      this.dragOver = 0;
  }

  ngOnInit() {
    this.seats = [];
    this.cart.selectedSeats = [];
    this.mode = 0;
    this.start = true;
    this.isEntered = true;
    this.initializeSeatMap(this.rows, this.columns);
  }

  initializeSeatMap(rows, columns) {
    this.rows = rows;
    this.columns = columns;
    this.seatConfig = this.generateSeatConfig([]);
    this.seats = [];
    this.passlineseats = [];
    console.log(this.seatConfig);
    if (this.rows > 10 && this.columns > 10) {
      const a = document.getElementById('seatsDIV');
      a.classList.remove('seat-container');
      a.classList.add('seat-container-change');
    }

    if (this.rows > 24 && this.columns > 24) {
      const a = document.getElementById('seatsDIV');
      a.classList.remove('seat-container');
      a.classList.add('seat-container-change2');
    }
    this.processSeatChart(this.seatConfig);
  }

  onKeyRow(event) {
    if (parseInt(event.target.value) > 1) {
      this.rows = parseInt(event.target.value);
    } else {
      this.rows = 0;
    }

  }

  onKeyCol(event) {
    if (parseInt(event.target.value) > 1) {
      this.columns = parseInt(event.target.value);
    } else {
      this.columns = 0;
    }

  }

  generateSeatConfig(map: any): any {
    let x = [];
    let temp = {};
    let setup: '';
    if (this.start) {
      for (let i = 0; i < this.rows; i++) {
        setup = '';
        for (let j = 0; j < this.columns; j++) {
          setup += 'g';
        }
        console.log(setup);
        temp = {
          seat_label : i,
          layout :  setup
        };
        x.push(temp);
        console.log('MAP');
        console.log(x);
        this.start = false;
      }
      console.log('X');
      console.log(x);
    } else {
      x = map;

    }


    this.seatConfig = [
      {
        seat_price: 250,
        seat_map: x
      }
      ];
    console.log('SEAT CONFIG');
    console.log(this.seatConfig);
    return this.seatConfig;
  }

  someMethod(event) {
    alert(event.target.value);
  }





  public processSeatChart( mapData: any[] ) {
    const x = 0;
    const a = 'A';
    if ( mapData.length > 0 ) {
        let seatNoCounter = 1;
        for (let counter = 0; counter < mapData.length; counter++) {
          let rowLabel = '';
          const itemMap = mapData[counter].seat_map;

          // Get the label name and price
          rowLabel = 'Row ' + itemMap[0].seatLabel + ' - ';
          if ( itemMap[ itemMap.length - 1].seatLabel !== ' ' ) {
            rowLabel += itemMap[ itemMap.length - 1].seatLabel;
          } else {
            rowLabel += itemMap[ itemMap.length - 2].seatLabel;
          }
          rowLabel += ' : Rs. ' + mapData[counter].seat_price;

          itemMap.forEach(mapElement => {
            const mapObj = {
              seatRowLabel : mapElement.seatLabel,
              seats : [],
              seatPricingInformation : rowLabel
            };
            rowLabel = '';
            const seatValArr = mapElement.layout.split('');
            console.log('lookuphere');
            console.log(seatValArr);
            if ( this.seatChartConfig.newSeatNoForRow ) {
              seatNoCounter = 1; // Reset the seat label counter for new row
            }
            let totalItemCounter = 1;

            seatValArr.forEach(item => {
              if ( item === 'g') {
                this.status = 'available';
              } else if (item === 'v') {
                this.status = 'vip';
              } else {
                this.status = 'passline';
              }
              const seatObj = {
                key : mapElement.seat_label + '_' + totalItemCounter,
                price : mapData[counter].seat_price,
                status :  this.status,
                event_id : 1,
                seatLabel : '',
                seatNo : ''
              };

              if ( item === 'g') {
                seatObj.price = '250';
                seatObj.seatLabel = mapElement.seat_label + ' ' + seatNoCounter;
                if (seatNoCounter < 10) { seatObj.seatNo = '0' + seatNoCounter; } else { seatObj.seatNo = '' + seatNoCounter; }
                seatNoCounter++;
              } else if (item === 'v') {
                seatObj.price = '500';
                seatObj.seatLabel = mapElement.seat_label + ' ' + seatNoCounter;
                if (seatNoCounter < 10) { seatObj.seatNo = '0' + seatNoCounter; } else { seatObj.seatNo = '' + seatNoCounter; }
                seatNoCounter++;
              } else {
                seatObj.seatLabel = '';
              }
              totalItemCounter++;
              if (this.mode === 0) {
                this.seats.push(seatObj);
              }

              mapObj.seats.push(seatObj);
            });
            console.log(' \n\n\n Seat Objects ' , mapObj);
            this.seatmap.push( mapObj );

          });
        }

      }
  }


  getNextLetter(char: string): string {
    let code: number = char.charCodeAt(0);
    code = code + 1;
    const help: string = String.fromCharCode(code);
    return help;
}

  changeMode() {
    if (this.mode === 0) {
      return true;
    } else {
      return false;
    }
  }

  changeModePassline() {
    if (this.mode === 1) {
      this.selectedSeat = [];
      return true;
    } else {
      return false;
    }
  }

  onMouseOver(ev, item) {
    if (ev.which !== 1) {
        return false;
    }

    ev.preventDefault();

    if (ev.type === 'mouseenter' && !item.selected) {
        this.dragOver = ev.clientY - this.dragStart > 0 ? 1 : -1;
        this.selectSeatMode(item);
        return false;
    }

    if (ev.type === 'mouseleave') {
        if (this.dragOver === 1 && ev.clientY < ev.target.offsetTop && item.selected) {
            console.log('desel...', item);
            this.selectSeatMode(item);
            return false;
        }
        if (this.dragOver === -1 && ev.clientY > ev.target.offsetTop && item.selected) {
            console.log('desel...', item);
            this.selectSeatMode(item);
            return false;
        }
    }
}

  public selectSeatMode(seatObject: any) {
    if (this.mode === 0) {
      this.status = 'vip';
      this.selectSeat(seatObject);
    } else if (this.mode === 1) {
      this.status = 'passline';
      this.selectSeat(seatObject);
    } else {
      alert('here');
    }

  }

  public selectVIPSeat(seatObject: any) {
    alert('vip seats');
    this.status = 'vip';
    this.selectSeat(seatObject);

  }


   positiveValues() {
     if (this.start) {
        if ((this.rows > 1 && this.columns > 1)) {
          return true;
      } else {
        return false;
      }
     }
     return true;

   }

  public selectSeat( seatObject: any) {
    console.log( 'Seat to block: ' , seatObject );
    if (seatObject.status === 'available') {
      seatObject.status = this.status;
      if (seatObject.status === 'passline') {
        this.passlineseats.push(seatObject.seatLabel);
      }
      this.cart.selectedSeats.push(seatObject.seatLabel);
      this.selectedSeat.push(seatObject.seatLabel);
      this.cart.seatstoStore.push(seatObject.key);
      this.cart.totalamount += seatObject.price;
      console.log(this.cart.selectedSeats);
      console.log(this.cart.seatstoStore);
    } else if ( seatObject.status === 'vip' ) {

      seatObject.status = 'available';
      const seatIndex = this.cart.selectedSeats.indexOf(seatObject.seatLabel);
      if ( seatIndex > -1) {
        this.cart.selectedSeats.splice(seatIndex , 1);
        this.cart.seatstoStore.splice(seatIndex , 1);
        this.cart.totalamount -= seatObject.price;
      }

    } else if ( seatObject.status === 'passline' ) {

      seatObject.status = 'available';
      const seatIndex = this.cart.selectedSeats.indexOf(seatObject.seatLabel);
      const seatIndex2 = this.passlineseats.indexOf(seatObject.seatLabel);

      if ( seatIndex > -1) {
        this.passlineseats.splice(seatIndex2, 1);
        this.cart.selectedSeats.splice(seatIndex , 1);
        this.cart.seatstoStore.splice(seatIndex , 1);
        this.cart.totalamount -= seatObject.price;

      }

    }
  }

  public blockSeats(seatsToBlock: string) {
    if (seatsToBlock !== '') {
      const seatsToBlockArr = seatsToBlock.split(',');
      for (let index = 0; index < seatsToBlockArr.length; index++) {
        const seat =  seatsToBlockArr[index] + '';
        const seatSplitArr = seat.split('_');
        console.log('Split seat: ' , seatSplitArr);
        for (let index2 = 0; index2 < this.seatmap.length; index2++) {
          const element = this.seatmap[index2];
          if (element.seatRowLabel === seatSplitArr[0]) {
            const seatObj = element.seats[parseInt(seatSplitArr[1]) - 1];
            if (seatObj) {
              console.log('\n\n\nFount Seat to block: ' , seatObj);
              seatObj.status = 'unavailable';
              this.seatmap[index2].seats[parseInt(seatSplitArr[1]) - 1] = seatObj;
              console.log('\n\n\nSeat Obj' , seatObj);
              console.log(this.seatmap[index2].seats[parseInt(seatSplitArr[1]) - 1]);
              break;
            }

          }
        }

      }
    }

  }

  public createSeatMap(exp: string, columns, rows, add) {


    const j  = 0;
    let temp = {};
    let start = 0;
    let end: number;
    end = this.columns;
    const map = [];
    exp = exp.replace(/\n/g, '');
    console.log('STRINGS');
    console.log(exp);
    for (let i = 0; i < this.columns + add; i++) {
      console.log('start '  + start);
      console.log('end '  + end);
      console.log('columns' + this.columns);
      console.log('exp len' +  exp.length);
      temp = {
        seat_label : i,
        layout :  exp.substring(start, end)
      };
      map.push(temp);
      start = end;
      const x: number = end + this.columns;

      if ( (end + this.columns) > exp.length) {
        console.log('Modus ' + exp.length % this.columns);
        end  = end + (exp.length % this.columns) + 1;
      } else {

        end = end + this.columns;
      }

    }
    console.log('MPTINA');
    console.log(map);
    return this.generateSeatConfig(map);
  }

  ifMode2Active() {
    if (this.mode === 2) {
      return true;
    } else {
      return false;
    }
  }

  ifMode3Active() {
    if (this.mode === 3) {
      return true;
    } else {
      return false;
    }
  }


  deleteChoice() {
    this.isEntered =  false;
    this.start = true;
    this.rows = 0;
    this.columns  = 0;
    this.seatmap = [];
  }
  processBooking(rows, columns, event = null) {
   let exp = '';
   const j = 0;
   if (this.mode < 2) {
     console.log(this.cart.selectedSeats);
     for (let i = 0; i < this.cart.selectedSeats.length; i++) {
      const a = document.getElementById('s_seat' + this.cart.selectedSeats[i]);
      a.classList.add('disabled');
     }
     if (this.mode === 1) {
      exp  = this.generateExpression(this.seats, exp, j, 0, rows, columns);
      console.log(exp);

    }
     this.mode = this.mode + 1;
     this.seatingObject.seatsP = this.seats;
     this.seatingObject.rowsP = this.rows;
     this.seatingObject.columnsP = this.columns;
     this.data.setEvent(this.seatingObject);


   } else {
      this.seats = event.hall.seats;
      this.rows = event.hall.totalRows;
      this.columns = event.hall.totalColumns;
      this.generateExpression(event.hall.seats, '', 0, 1, event.hall.totalRows, event.hall.totalColumns);
   }
  }



  createExpressionForCreationOfEvent() {

  }

  isCreated() {
    if (this.mode < 2) {
      return true;
    }
  }

  generateExpression(seats, exp, j, context: number, rows, columns) {
    console.log('SEATS');
    console.log(seats);
    let add = 0;
    for (let i = 0; i < seats.length; i++) {

      if (context === 0) {
        if (seats[i].status === 'available') {
          exp += 'g';
       } else if (seats[i].status === 'vip') {
          exp += 'v';
       } else {
          exp += 'x';
       }
        if (j === columns - 1) {
        exp += '\n';
        j = 0;
        } else {
          j += 1;
        }
        add = 1;
      } else {
        add = 0;
        if (seats[i].typeOfSeat === 'AVAILABLE') {
          exp += 'g';
       } else if (seats[i].typeOfSeat === 'VIP') {
          exp += 'v';
       } else {
          exp += 'x';
       }
        if (j === columns - 1) {
        exp += '\n';
        j = 0;
        } else {
          j += 1;
        }

      }
    }
    console.log(exp);
    console.log(columns);
    console.log(rows);
    console.log(add);
    this.seatConf = this.createSeatMap(exp, this.columns, this.rows, add);
    console.log(this.seatConf);
    this.seatmap = [];
    this.processSeatChart(this.seatConf);

    return exp;
  }

  buyingCheck() {
    if (this.isEntered && this.forBuying && this.mode === 3) {
      alert('checking');
      return true;
    } else {
      return false;
    }
  }
}
