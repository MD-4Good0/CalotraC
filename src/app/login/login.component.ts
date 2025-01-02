import { Component, OnInit } from '@angular/core';
import { userModel } from '../userModel';
import { ServiceService } from '../service.service';
import {Router} from '@angular/router'
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  user: userModel=new userModel();

  constructor(private service: ServiceService, private router: Router, private appComponent:AppComponent){}

  ngOnInit(): void {
    this.appComponent.showNavbar=false;
  }

  onSubmit(){
    console.log(this.user);
    this.login();
  }

  getUser(){
    this.service.home(this.user.email_id)
    .subscribe({
      next: (data: any) => {
        this.user=data;
        this.appComponent.user_id=this.user.user_id;
      },
      complete: () => this.router.navigate(['home',this.user.user_id]) 
    })
  }

  login(){
    this.service.loginUser(this.user)
    .subscribe({
      next: (data: any) => { // Use a more descriptive type than 'any'
        // console.log(data);
       
          this.appComponent.user = this.user;
          // console.log(this.appComponent.user);
          
        // console.log(data.token);
        localStorage.setItem('emailId',this.user.email_id);
        // localStorage.setItem('userId',this.user.user_id);
        localStorage.setItem('token', data.token); // Use string literal for clarity
      },
      // error: (e) => Swal.fire('Error', 'Wrong Credentials', 'error')
      complete: () => this.getUser()
    })

  }

  register(){
    this.router.navigate(['register'])
  }
}
