import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';

import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule } from 'ngx-bootstrap/modal';
import { LoginComponent } from './components/login/login.component';
import {RouterModule, Routes} from '@angular/router'
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { AuthGuardGuard } from './services/security/auth-guard.guard';
import { JwtUtilsService } from './services/security/jwt-utils.service';
import { HttpClientModule } from '@angular/common/http'; 
import { AuthenticationService } from './services/security/authentication-service.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptorService } from './services/security/token-interceptor.service';
import { LoginGuardGuard } from './services/security/login-guard.guard';
import {EventService} from './services/event.service';
import { SeatSelectionComponent } from './components/seat-selection/seat-selection.component';
import { DragToSelectModule } from 'ngx-drag-to-select';
import {TicketReservationComponent} from './components/ticket-reservation/ticket-reservation.component'
import { TicketReservationService } from './services/ticket-reservation.service';

const  appRoutes = [
  {
    path:'login',
    component: LoginComponent,
    canActivate : [LoginGuardGuard]
  },
  {
    path : 'seatmap',
    component : SeatSelectionComponent
  },{
    path : 'reservation',
    component : TicketReservationComponent
  }

];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SeatSelectionComponent,
    TicketReservationComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true }), // <-- debugging purposes only) 
    //forRoot() da bi imala globalni opseg
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    HttpClientModule,
    DragToSelectModule.forRoot()
  ],
  providers: [
    AuthGuardGuard,
    JwtUtilsService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },
    AuthenticationService,
    EventService,
    TicketReservationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
