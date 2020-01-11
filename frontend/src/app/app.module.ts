import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule }   from '@angular/forms';

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

const  appRoutes = [
  {
    path:'login',
    component: LoginComponent,
    canActivate : [LoginGuardGuard]
  },
  {
    path : 'seatmap',
    component : SeatSelectionComponent
  }

];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SeatSelectionComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true }), // <-- debugging purposes only) 
    //forRoot() da bi imala globalni opseg
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    HttpClientModule
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
    EventService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
