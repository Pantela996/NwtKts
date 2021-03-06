import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule } from 'ngx-bootstrap/modal';
import { LoginComponent } from './components/login/login.component';
import {RouterModule, Routes} from '@angular/router';
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
import {TicketReservationComponent} from './components/ticket-reservation/ticket-reservation.component';
import { TicketReservationService } from './services/ticket-reservation.service';
import { CreateLocationAdminComponent } from './components/create-location-admin/create-location-admin.component';
import { ReadDeleteLocationAdminComponent } from './components/read-delete-location-admin/read-delete-location-admin.component';
import {SuperUserService} from './services/super-user.service';
import { ReadDeleteLocationComponent } from './components/read-delete-location/read-delete-location.component';
import { ReadDeleteUsersComponent } from './components/read-delete-users/read-delete-users.component';
import { CreateLocationComponent } from './components/create-location/create-location.component';
import { ReadUpdateLocationComponent } from './components/read-update-location/read-update-location.component';
import { CreateEventComponent } from './components/create-event/create-event.component';
import { componentFactoryName } from '@angular/compiler';
import { RegisterComponent } from './components/register/register.component';
import { MyTicketsComponent } from './components/my-tickets/my-tickets.component';
import { RegularUserService } from './services/regular-user.service';
import { ProfitReviewComponent } from './components/profit-review/profit-review.component';

const  appRoutes = [
  {
    path: 'login',
    component: LoginComponent,
    canActivate : [LoginGuardGuard]
  },
  {
    path : 'reservation',
    component : TicketReservationComponent
  }, {
    path : 'create_location_admin',
    component: CreateLocationAdminComponent
  }, {
    path: 'read_delete_location_admin',
    component: ReadDeleteLocationAdminComponent
  }, {
    path: 'reservation/:id', component: TicketReservationComponent
  }, {
    path: 'read_delete_location',
    component: ReadDeleteLocationComponent
  }, {
    path: 'read_delete_users',
    component: ReadDeleteUsersComponent
  }, {
    path: 'create_location',
    component: CreateLocationComponent
  }, {
    path: 'read_update_location',
    component: ReadUpdateLocationComponent
  }, {
    path: 'create_event',
    component: CreateEventComponent
  }, {
    path: 'register',
    component: RegisterComponent
  }, {
    path: 'my_tickets',
    component: MyTicketsComponent
  }, {
    path: 'profit_review',
    component: ProfitReviewComponent
  }


];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SeatSelectionComponent,
    TicketReservationComponent,
    CreateLocationAdminComponent,
    ReadDeleteLocationAdminComponent,
    ReadDeleteLocationComponent,
    ReadDeleteUsersComponent,
    CreateLocationComponent,
    ReadUpdateLocationComponent,
    CreateEventComponent,
    RegisterComponent,
    MyTicketsComponent,
    ProfitReviewComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true }), // <-- debugging purposes only)
    // forRoot() da bi imala globalni opseg
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
    TicketReservationService,
    SuperUserService,
    SeatSelectionComponent,
    RegularUserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
