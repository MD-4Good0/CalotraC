import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { ServiceService } from '../service.service';
import { userModel } from '../userModel';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {

  user:userModel=new userModel(); // Updated user_id to be optional
  user_id:number;

  constructor(private service: ServiceService, private router: Router, private route: ActivatedRoute,  private appComponent:AppComponent) {
    this.user_id=0;
  }

  ngOnInit(): void {
    // this.user_id = this.appComponent.user.user_id; // Convert user_id to a number
    
  }

  logout(): void{
    localStorage.clear();
    this.router.navigate(['login']);
  }

  detail(): void {
    this.user_id = this.appComponent.user_id;
    console.log(this.user_id);
    
    if (this.user_id) {
      this.router.navigate(['detail', this.user_id]); // Navigate to detail with user_id if defined
    } else {
      console.error('User ID is not defined.'); // Handle case where user_id is not defined
    }
  }

  home(): void {
    this.user_id = this.appComponent.user_id;
    console.log(this.user_id);
    if (this.user_id) {
      this.router.navigate(['home', this.user_id]); // Navigate to home with user_id if defined
    } else {
      console.error('User ID is not defined.'); // Handle case where user_id is not defined
    }
  }
}
