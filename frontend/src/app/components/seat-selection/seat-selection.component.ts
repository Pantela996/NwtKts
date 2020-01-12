import { Component, OnInit } from '@angular/core';
import { Inject }  from '@angular/core';
import { DOCUMENT } from '@angular/common'; 

@Component({
  selector: 'app-seat-selection',
  templateUrl: './seat-selection.component.html',
  styleUrls: ['./seat-selection.component.css']
})
export class SeatSelectionComponent implements OnInit {




  constructor(@Inject(DOCUMENT) document) {

   }

  ngOnInit() {
  }

  private seatConfig: any = null;
  private seatmap = [];
  isEntered = false;
  forBuying = false;
  rows :number;
  columns : number;
  status:string;
  mode = 0;
  selectedSeat = [];
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
  
  initializeSeatMap(){
    this.isEntered = true;
    this.seatConfig = this.generateSeatConfig(this.rows,this.columns,0);
    console.log(this.seatConfig);
    this.processSeatChart(this.seatConfig);
  }

  onKeyRow(event) {
    this.rows = event.target.value;
  }

  onKeyCol(event) {
    this.columns = event.target.value;
  }
  
  generateSeatConfig(rows, columns, seat_price):any{

    var x = [];
    var temp = {};
    this.isEntered = true;
    var setup:"";
    for (let i = 0; i < rows; i++) {
      setup = "";
      for (let j = 0; j < columns; j++){
        setup += "g";
      }
      console.log(setup);
      temp = {
        "seat_label" : i,
        "layout" :  setup
      }
      x.push(temp);
    }
    
    this.seatConfig = [
      {
        "seat_price": 250,
        "seat_map":x
      }
      ]
    return this.seatConfig;   
  }


  public processSeatChart ( map_data : any[] )
  {
    
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
            if( this.seatChartConfig.newSeatNoForRow )
            {
              seatNoCounter = 1; //Reset the seat label counter for new row
            }
            var totalItemCounter = 1;
            seatValArr.forEach(item => {
              var seatObj = {
                "key" : map_element.seat_label+"_"+totalItemCounter,
                "price" : map_data[__counter]["seat_price"],
                "status" : "available"
              };
               
              if( item == 'g')
              {
                seatObj["seatLabel"] = map_element.seat_label+" "+seatNoCounter;
                if(seatNoCounter < 10)
                { seatObj["seatNo"] = "0"+seatNoCounter; }
                else { seatObj["seatNo"] = ""+seatNoCounter; }
                
                seatNoCounter++;
              }else if(item == 'v'){
                seatObj["seatNo"] = "A" + seatNoCounter;
                alert("here");
              }
              else
              {
                seatObj["seatLabel"] = "";
              }
              totalItemCounter++;
              mapObj["seats"].push(seatObj);
            });
            console.log(" \n\n\n Seat Objects " , mapObj);
            this.seatmap.push( mapObj );

          });
        }

      }
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

  public selectVIPSeat(seatObject : any){
    this.status = "vip";

    this.selectSeat(seatObject);

  }


   

  public selectSeat( seatObject : any)
  {
    console.log( "Seat to block: " , seatObject );
    if(seatObject.status == "available")
    {
      seatObject.status = this.status;
      this.cart.selectedSeats.push(seatObject.seatLabel);
      this.selectedSeat.push(seatObject.seatLabel);
      this.cart.seatstoStore.push(seatObject.key);
      this.cart.totalamount += seatObject.price;
      console.log(this.cart.selectedSeats);
      console.log(this.cart.seatstoStore);
    }
    else if( seatObject.status = "vip" )
    {
      seatObject.status ="available";
      var seatIndex = this.cart.selectedSeats.indexOf(seatObject.seatLabel);
      if( seatIndex > -1)
      {
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
  processBooking(){
   console.log(this.cart.selectedSeats);
   this.mode = 1;
   for(let i = 0; i < this.cart.selectedSeats.length;i++){
    var a = document.getElementById("s_seat"+this.cart.selectedSeats[i]);
    a.classList.add("disabled");
   }

   
  }
}
