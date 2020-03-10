import { Component, OnInit, Input, HostListener } from '@angular/core';
import { TransferService } from 'src/app/services/transfer.service';
import { ActivatedRoute } from '@angular/router';
import { EventService } from 'src/app/services/event.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';


@Component({
  selector: 'app-ticket-reservation',
  templateUrl: './ticket-reservation.component.html',
  styleUrls: ['./ticket-reservation.component.css']
})
export class TicketReservationComponent implements OnInit {

  constructor(private transferService: TransferService, private activatedRoute: ActivatedRoute,
              private router: Router, private eventService: EventService) {
    this.seatingObject = {seatsP: [], rowsP: 0, columnsP: 0};

   }
  private event;
  private id;
  private context;



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

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
      this.id = params.get('id');
      this.setCurrent(this.id);
    });
  }

  setCurrent(eventId: string) {
    this.eventService.getOne(eventId).then(
      event => {
      this.event = event;
      this.goOn();
      },
      err => console.log(err),
    );
  }

  goOn() {
    this.mode = 3;
    this.context = 1;
    this.start = false;

    this.f1().then(res => this.f2());

  }


  f1() {
    return new Promise((resolve, reject) => {
      this.processBooking(this.event);
    });
  }

  f2() {
    this.router.navigate(['/']);
  }


  setEvent(data) {
    this.event = data;
  }

  getCurrent() {
    return this.event;
  }


  @HostListener('document:mousedown', ['$event'])
  onMouseDown(ev) {
      this.dragStart = ev.clientY;
  }
  @HostListener('document:mouseup', ['$event'])
  onMouseUp(ev) {
      this.dragStart = 0;
      this.dragOver = 0;
  }




  initializeSeatMap() {
    this.cart.selectedSeats = [];
    this.mode = 0;
    this.start = true;
    this.isEntered = true;
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

  generateSeatConfig(map: any, exp = ''): any {
    let x = [];
    let temp = {};
    this.isEntered = true;
    let setup: '';
    if (this.start) {
      for (let i = 0; i < this.rows; i++) {
        setup = '';
        for (let j = 0; j < this.columns; j++) {
          setup += 'v';
        }
        console.log(setup);
        temp = {
          seat_label : i,
          layout :  setup
        };
        x.push(temp);
        this.start = false;
      }
    } else {
      x = map;
    }


    this.seatConfig = [
      {
        seat_price: 250,
        seat_map: x
      }
      ];
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
        this.seats = [];
        let seatNoCounter = 1;
        for (let counter = 0; counter < mapData.length; counter++) {
          let rowLabel = '';
          const itemMap = mapData[counter].seat_map;

          // Get the label name and price
          rowLabel = 'Row ' + itemMap[0].seat_label + ' - ';
          if ( itemMap[ itemMap.length - 1].seat_label !== ' ' ) {
            rowLabel += itemMap[ itemMap.length - 1].seat_label;
          } else {
            rowLabel += itemMap[ itemMap.length - 2].seat_label;
          }
          rowLabel += ' : Rs. ' + mapData[counter].seat_price;

          itemMap.forEach(mapElement => {
            const mapObj = {
              seatRowLabel : mapElement.seat_label,
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
              } else if (item === 'r') {
                this.status = 'reserved';
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
                seatObj.price = 250;
                seatObj.seatLabel = mapElement.seat_label + ' ' + seatNoCounter;
                if (seatNoCounter < 10) { seatObj.seatNo = '0' + seatNoCounter; } else { seatObj.seatNo = '' + seatNoCounter; }

                seatNoCounter++;
              } else if (item === 'v') {
                seatObj.price = 500;
                seatObj.seatLabel = mapElement.seat_label + ' ' + seatNoCounter;
                if (seatNoCounter < 10) { seatObj.seatNo = '0' + seatNoCounter; } else { seatObj.seatNo = '' + seatNoCounter; }
                seatNoCounter++;
              } else if (item === 'r') {
                seatObj.price = 500;
                seatObj.seatLabel = mapElement.seat_label + ' ' + seatNoCounter;
                if (seatNoCounter < 10) { seatObj.seatNo = '0' + seatNoCounter; } else { seatObj.seatNo = '' + seatNoCounter; }
                seatNoCounter++;
              } else {
                seatObj.seatLabel = '';
              }
              totalItemCounter++;
              if (this.mode === 3) {
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
      this.status = 'to_be_reserved';
      this.selectSeat(seatObject);
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
    // prepraviti ovu funckiju
    console.log( 'Seat to block: ' , seatObject );
    if (seatObject.status === 'available') {

      seatObject.prev_state = seatObject.status;
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
      if (this.mode !== 3) {
        seatObject.status = 'available';
        const seatIndex = this.cart.selectedSeats.indexOf(seatObject.seatLabel);
        if ( seatIndex > -1) {
          this.cart.selectedSeats.splice(seatIndex , 1);
          this.cart.seatstoStore.splice(seatIndex , 1);
          this.cart.totalamount -= seatObject.price;
        }
      } else {
        seatObject.prev_state = seatObject.status;
        seatObject.status = 'to_be_reserved';
        this.cart.selectedSeats.push(seatObject.seatLabel);
        this.selectedSeat.push(seatObject.seatLabel);
        this.cart.seatstoStore.push(seatObject.key);
        this.cart.totalamount += seatObject.price;
        console.log(this.cart.selectedSeats);
        console.log(this.cart.seatstoStore);
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

    } else if (seatObject.status === 'to_be_reserved') {
      seatObject.status = seatObject.prev_state;
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
    end = columns;
    const map = [];
    exp = exp.replace(/\n/g, '');
    console.log('STRINGS');
    console.log(exp);
    for (let i = 0; i < columns + add; i++) {
      console.log('start '  + start);
      console.log('end '  + end);
      console.log('columns' + columns);
      console.log('exp len' +  exp.length);
      temp = {
        seat_label : i,
        layout :  exp.substring(start, end)
      };
      map.push(temp);
      start = end;
      const x: number = end + columns;

      if ( (end + columns) > exp.length) {
        console.log('Modus ' + exp.length % columns);
        end  = end + (exp.length % columns) + 1;
      } else {

        end = end + columns;
      }

    }
    console.log(map);
    return this.generateSeatConfig(map, exp);
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
    this.router.navigate(['/']);
  }
  processBooking(event = null) {
   const exp = '';
   const j = 0;
   console.log(event);
   this.seats = event.hall.seats;
   this.rows = event.hall.totalRows;
   this.columns = event.hall.totalColumns;
   this.generateExpression('', 0,  event.hall.totalRows, event.hall.totalColumns);


  }



  createExpressionForCreationOfEvent() {

  }

  generateExpression(exp, j, rows, columns) {
    console.log('SEATS');
    console.log(this.seats);
    let add = 0;
    for (let i = 0; i < this.seats.length; i++) {

      if (this.context === 0) {
        if (this.seats[i].status === 'available') {
          exp += 'g';
       } else if (this.seats[i].status === 'vip') {
          exp += 'v';
       } else if (this.seats[i].status === 'reserved') {
         exp += 'r';
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
        if (this.seats[i].typeOfSeat === 'AVAILABLE') {
          exp += 'g';
       } else if (this.seats[i].typeOfSeat === 'VIP') {
          exp += 'v';
       } else if (this.seats[i].typeOfSeat === 'RESERVED') {
        exp += 'r';
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
    this.seatConf = this.createSeatMap(exp, columns, rows, add);
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

  sendOrder() {
    this.seatingObject.seatsP = this.seats;
    this.seatingObject.rowsP = this.rows;
    this.seatingObject.columnsP = this.columns;
    this.seatingObject.reservedSeats = [];
    for (let i = 0; i < this.cart.selectedSeats.length; i++) {
      for (let j = 0; j < this.seats.length; j++) {
        if (this.cart.selectedSeats[i] === this.seats[j].seatLabel) {
          this.seatingObject.reservedSeats.push(this.seats[j]);
        }
      }
    }
    console.log(this.seatingObject.reservedSeats);
    for (let i = 0; i < this.seats.length; i++) {
      if (this.seats[i].status === 'to_be_reserved') {
        this.seats[i].status = 'reserved';
      }
    }
    this.eventService.updateHallSeats(this.seatingObject, this.event).subscribe(success => {
      this.setRedirect(success);

    });
  }

  setRedirect(data) {
    this.router.navigate(['/']);

  }

  getData(data) {
    console.log('DATA IS HERE');
    console.log(data);
  }

}
