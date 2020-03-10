import { Component, OnInit } from '@angular/core';
import {SuperUserService} from 'src/app/services/super-user.service';
import { Router } from '@angular/router';
import { ThrowStmt } from '@angular/compiler';

@Component({
  selector: 'app-read-delete-location-admin',
  templateUrl: './read-delete-location-admin.component.html',
  styleUrls: ['./read-delete-location-admin.component.css']
})
export class ReadDeleteLocationAdminComponent implements OnInit {

  public users = [];

  constructor(private superuserservice: SuperUserService, private router: Router) { }

  ngOnInit() {
    this.superuserservice.getAll().subscribe(success => {this.setUsers(success); });
  }

  setUsers(data) {
    if (data.length !== 0) {
      this.users = data;
      console.log(this.users[0].username);

    }

  }
  addNewAdmin() {
    this.router.navigate(['/create_location_admin']);
  }

  onDelete(username: string) {
    this.users = this.users.filter(u => u.username !== username);
    this.superuserservice.deleteUser(username).subscribe();
  }


}
