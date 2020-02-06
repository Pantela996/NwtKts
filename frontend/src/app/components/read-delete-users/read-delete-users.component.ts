import { Component, OnInit } from '@angular/core';
import {SuperUserService} from "src/app/services/super-user.service"
import { Router } from '@angular/router';

@Component({
  selector: 'app-read-delete-users',
  templateUrl: './read-delete-users.component.html',
  styleUrls: ['./read-delete-users.component.css']
})
export class ReadDeleteUsersComponent implements OnInit {

  public users = [];

  constructor(private superuserservice: SuperUserService, private router:Router) { }

  ngOnInit() {
    this.superuserservice.getAllUsers().subscribe(success => {this.setUsers(success)});
  }

  setUsers(data){
    if(data.length != 0){
      this.users = data;
      console.log(this.users[0].username);

    }
  }

  onDelete(username:string){
    this.users = this.users.filter(u => u.username !== username);
    this.superuserservice.deleteRegularUser(username).subscribe();
  }


}
