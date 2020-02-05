import { Component, OnInit, HostListener } from '@angular/core';
import { Inject }  from '@angular/core';
import { DOCUMENT } from '@angular/common'; 
import { Router } from '@angular/router';
import { EventService } from 'src/app/services/event.service';


@Component({
  selector: 'app-seat-selection',
  templateUrl: './seat-selection.component.html',
  styleUrls: ['./seat-selection.component.css']
})
export class SeatSelectionComponent implements OnInit {
  

  public eventService:any;
  public start = true;
  private seatConfig: any = null;
  private seatmap = [];
  public isEntered = false;
  public forBuying = false;
  public rows :number;
  public columns : number;
  public status:string;
  public mode = 0;
  private seats = [];
  public selectedSeat = [];
  public passlineseats = [];
  public seatingObject:any;
  public seatsP;
  public rowsP;
  public columnsP;
  private event_id;
  private available_seats = [];
  public selectedDocuments = [];
  private dragStart:number = 0;
  private dragOver:number = 0;
  private seat_conf:any;
  private seatChartConfig = {
    showRowsLabel : false,
    showRowWisePricing : false,
    newSeatNoForRow : false
  }
  
  private cart = {
    selectedSeats : [],
    seatstoStore : [],
    totalamount : 0,
    cartId : "",
    eventId : 0
  };

  
  @HostListener('document:mousedown', ['$event'])
  onMouseDown(ev) {
      this.dragStart = ev.clientY;
  }
  @HostListener('document:mouseup', ['$event'])
  onMouseUp(ev) {
      this.dragStart = 0;
      this.dragOver = 0;
  }
  

  constructor(@Inject(DOCUMENT) document, eventService:EventService, public router:Router,) {
    this.eventService  = eventService;
    this.seatingObject = {seatsP:[],rowsP:0,columnsP:0};
   }

  ngOnInit() {
    this.mode = 0;
    this.start = true;
    this.isEntered = false;
    this.rows = 0;
    this.columns = 0;
    this.cart.selectedSeats = [];
    this.seats = [];
    this.passlineseats = [];
    
  }
 
  initializeSeatMap(){
    this.cart.selectedSeats = [];
    this.mode = 0;
    this.start = true;
    this.isEntered = true;
    this.seatConfig = this.generateSeatConfig([]);
    this.seats = [];
    this.passlineseats = [];
    console.log(this.seatConfig);
    if(this.rows > 10 && this.columns > 10){
      var a = document.getElementById("seatsDIV");
      a.classList.remove("seat-container");
      a.classList.add("seat-container-change");
    }

    if(this.rows > 24 && this.columns > 24){
      var a = document.getElementById("seatsDIV");
      a.classList.remove("seat-container");
      a.classList.add("seat-container-change2");
    }
    this.processSeatChart(this.seatConfig);
  }

  onKeyRow(event) {
    if(parseInt(event.target.value) > 1){
      this.rows = parseInt(event.target.value);
    }else{
      this.rows = 0;
    }
    
  }

  onKeyCol(event) {
    if(parseInt(event.target.value) > 1){
      this.columns = parseInt(event.target.value);
    }else{
      this.columns = 0;
    }
    
  }
  
  generateSeatConfig(map:any):any{

    var x = [];
    var temp = {};
    this.isEntered = true;
    var setup:"";
    if(this.start){
      for (let i = 0; i < this.rows; i++) {
        setup = "";
        for (let j = 0; j < this.columns; j++){
          setup += "g";
        }
        console.log(setup);
        temp = {
          "seat_label" : i,
          "layout" :  setup
        }
        x.push(temp);
        this.start = false;
      }
    }else{
      x = map;
    }
   
    
    this.seatConfig = [
      {
        "seat_price": 250,
        "seat_map":x
      }
      ]
    console.log(this.seatConfig);
    return this.seatConfig;   
  }

  someMethod(event){
    alert(event.target.value);
  }

  public processSeatChart ( map_data : any[] )
  {
    var x:number = 0;
    var a:string = "A";
      if( map_data.length > 0 )
      {
        var seatNoCounter = 1;
        for (let __counter = 0; __counter < map_data.length; __counter++) {
          var row_label = "";
          var item_map = map_data[__counter].seat_map;

          //Get the label name and price
          row_label = "Row "+item_map[0].seat_label + " - ";
          if( item_map[ item_map.length - 1].seat_label != " " )
          {
            row_label += item_map[ item_map.length - 1].seat_label;
          }
          else
          {
            row_label += item_map[ item_map.length - 2].seat_label;
          }
          row_label += " : Rs. " + map_data[__counter].seat_price;
          
          item_map.forEach(map_element => {
            var mapObj = {
              "seatRowLabel" : map_element.seat_label,
              "seats" : [],
              "seatPricingInformation" : row_label
            };
            row_label = "";
            var seatValArr = map_element.layout.split('');
            console.log("lookuphere");
            console.log(seatValArr);
            if( this.seatChartConfig.newSeatNoForRow )
            {
              seatNoCounter = 1; //Reset the seat label counter for new row
            }
            var totalItemCounter = 1;

            seatValArr.forEach(item => {
              if( item == 'g'){
                this.status = "available";
              }else if(item == "v"){
                this.status = "vip";
              }else{
                this.status = "passline";
              }
              var seatObj = {
                "key" : map_element.seat_label+"_"+totalItemCounter,
                "price" : map_data[__counter]["seat_price"],
                "status" :  this.status,
                "event_id" : 0
              };
               
              if( item == 'g')
              {
                seatObj["seatLabel"] = map_element.seat_label+" "+seatNoCounter;
                if(seatNoCounter < 10)
                { seatObj["seatNo"] = "0"+seatNoCounter; }
                else { seatObj["seatNo"] = ""+seatNoCounter; }
                
                seatNoCounter++;
              }else if(item == 'v'){
                seatObj["seatLabel"] = map_element.seat_label+" "+seatNoCounter;
                if(seatNoCounter < 10){ seatObj["seatNo"] = "0"+seatNoCounter; }
                else { seatObj["seatNo"] = ""+seatNoCounter; }
                seatNoCounter++;
              }
              else{
                seatObj["seatLabel"] = "";
              }
              totalItemCounter++;
              if(this.mode == 0){
                this.seats.push(seatObj);
              }
              
              mapObj["seats"].push(seatObj);
            });
            console.log(" \n\n\n Seat Objects " , mapObj);
            this.seatmap.push( mapObj );

          });
        }

      }
  }


  getNextLetter(char: String): String {
    var code: number = char.charCodeAt(0);
    code = code + 1;
    var help:string = String.fromCharCode(code);
    return help;
}

  changeMode(){
    if (this.mode == 0){
      return true;
    }else{
      return false;
    }
  }

  changeModePassline(){
    if(this.mode==1){
      this.selectedSeat = [];
      return true;
    }else{
      return false;
    }
  }

  onMouseOver(ev, item) {
    if(ev.which!==1) {
        return false;
    }

    ev.preventDefault();

    if(ev.type==='mouseenter' && !item.selected) {
        this.dragOver = ev.clientY - this.dragStart > 0 ? 1:-1;
        this.selectSeatMode(item);
        return false;
    }

    if(ev.type==='mouseleave') {
        if(this.dragOver===1 && ev.clientY < ev.target.offsetTop && item.selected) {
            console.log('desel...', item);
            this.selectSeatMode(item);
            return false;
        }
        if(this.dragOver===-1 && ev.clientY > ev.target.offsetTop && item.selected) {
            console.log('desel...', item);
            this.selectSeatMode(item);
            return false;
        }
    }
}
    
  public selectSeatMode(seatObject : any){
    if(this.mode==0){
      this.status = "vip";
      this.selectSeat(seatObject);  
    }else if(this.mode==1){
      this.status = "passline";
      this.selectSeat(seatObject);  
    }else{
      alert("here");
    }

  }

  public selectVIPSeat(seatObject : any){
    alert("vip seats");
    this.status = "vip";
    this.selectSeat(seatObject);

  }


   positiveValues(){
     if(this.start){
        if ((this.rows >1 && this.columns > 1)){
          return true;
      }else{
        return false;
      }
     }
     return true;
     
   }

  public selectSeat( seatObject : any)
  {
    console.log( "Seat to block: " , seatObject );
    if(seatObject.status == "available")
    {
      seatObject.status = this.status;
      if(seatObject.status == "passline"){
        this.passlineseats.push(seatObject.seatLabel);
      }
      this.cart.selectedSeats.push(seatObject.seatLabel);
      this.selectedSeat.push(seatObject.seatLabel);
      this.cart.seatstoStore.push(seatObject.key);
      this.cart.totalamount += seatObject.price;
      console.log(this.cart.selectedSeats);
      console.log(this.cart.seatstoStore);
    }
    else if( seatObject.status == "vip" )
    {

      seatObject.status ="available";
      var seatIndex = this.cart.selectedSeats.indexOf(seatObject.seatLabel);
      if( seatIndex > -1)
      {
        this.cart.selectedSeats.splice(seatIndex , 1);
        this.cart.seatstoStore.splice(seatIndex , 1);
        this.cart.totalamount -= seatObject.price;
      }
      
    } else if( seatObject.status == "passline" )
    {
      
      seatObject.status ="available";
      var seatIndex = this.cart.selectedSeats.indexOf(seatObject.seatLabel);
      var seatIndex2 = this.passlineseats.indexOf(seatObject.seatLabel);
      
      if( seatIndex > -1)
      {
        this.passlineseats.splice(seatIndex2,1);
        this.cart.selectedSeats.splice(seatIndex , 1);
        this.cart.seatstoStore.splice(seatIndex , 1);
        this.cart.totalamount -= seatObject.price;
        
      }
      
    }
  }

  public blockSeats(seatsToBlock : string)
  {
    if(seatsToBlock != "")
    {
      var seatsToBlockArr = seatsToBlock.split(',');
      for (let index = 0; index < seatsToBlockArr.length; index++) {
        var seat =  seatsToBlockArr[index]+"";
        var seatSplitArr = seat.split("_");
        console.log("Split seat: " , seatSplitArr);
        for (let index2 = 0; index2 < this.seatmap.length; index2++) {
          const element = this.seatmap[index2];
          if(element.seatRowLabel == seatSplitArr[0])
          {
            var seatObj = element.seats[parseInt(seatSplitArr[1]) - 1];
            if(seatObj)
            {
              console.log("\n\n\nFount Seat to block: " , seatObj);
              seatObj["status"] = "unavailable";
              this.seatmap[index2]["seats"][parseInt(seatSplitArr[1]) - 1] = seatObj;
              console.log("\n\n\nSeat Obj" , seatObj);
              console.log(this.seatmap[index2]["seats"][parseInt(seatSplitArr[1]) - 1]);
              break;
            }
             
          }
        }
       
      }
    }
    
  }

  public createSeatMap(exp:string){
    var j:number  = 0;
    var temp = {};
    var start =0;
    var end:number;
    end = this.columns;
    var map = [];
    exp = exp.replace(/\n/g, '')
    console.log("STRINGS");
    console.log(exp);
    for(let i =0; i < this.columns + 1;i++){
      console.log("start "  + start);
      console.log("end "  + end);
      console.log("columns" + this.columns);
      console.log("exp len" +  exp.length);
      temp = {
        "seat_label" : i,
        "layout" :  exp.substring(start, end)
      }
      map.push(temp);
      start = end;
      var x:number = end +this.columns;

      if( (end + this.columns) > exp.length){
        console.log("Modus " + exp.length%this.columns);
        end  = end + (exp.length%this.columns) + 1;
      }else{

        end = end + this.columns;
      }
      
    }
    console.log(map);
    return this.generateSeatConfig(map);
  }

  ifMode2Active(){
    if(this.mode == 2){
      return true;
    }else{
      return false;
    }
  }


  deleteChoice(){
    this.isEntered =  false;
    this.start = true;
    this.rows = 0;
    this.columns  =0;
    this.seatmap = [];
  }
  processBooking(){
   var exp="";
   var j = 0;
  
   if(this.mode < 2){
     console.log(this.cart.selectedSeats);
     for(let i = 0; i < this.cart.selectedSeats.length;i++){
      var a = document.getElementById("s_seat"+this.cart.selectedSeats[i]);
      a.classList.add("disabled");
     }
     if(this.mode == 1){
      exp  = this.generateExpression(exp,j, this.seats);
    }
    this.mode = this.mode + 1;
   }else{

      this.seatingObject.seatsP = this.seats;
      this.seatingObject.rowsP = this.rows;
      this.seatingObject.columnsP = this.columns;
      this.eventService.createHallSeats(this.seatingObject).then(() => {
        alert("Successfully");
        this.router.navigate(['/']);
      });

   }
  }

  generateExpression(exp,j, seats){
    for (let i = 0; i < seats.length; i++){
       
      if(seats[i].status == "available"){
         exp += "g";
      }else if(seats[i].status == "vip"){
         exp += "v";
      }else{
         exp += "x";
      }
      if(j == this.columns-1){
       exp += "\n";
         j = 0;
       }else{
         j += 1;
       }
  
    }

    this.seat_conf = this.createSeatMap(exp);
    console.log(this.seat_conf);
    this.seatmap = [];
    this.processSeatChart(this.seat_conf);
    return exp;
  }
}
