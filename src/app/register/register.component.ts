import { Component, OnInit } from '@angular/core';
import { userModel } from '../userModel';
import { ServiceService } from '../service.service';
import {Router} from '@angular/router'
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{

  user: userModel=new userModel();

  constructor(private service: ServiceService, private router: Router, private appComponent:AppComponent){}

  ngOnInit(): void {
    this.appComponent.showNavbar=false;
  }

  onSubmit(){
    console.log(this.user);
    this.regProvider();
  }

  regProvider(){
    this.service.registerUser(this.user)
    .subscribe({
      next: (v) =>{
        // this.prov=v;
        //  Swal.fire('Registered', 'RP ID: '+this.prov.rpID, 'success')},
        console.log(this.user)},
      // error: (e) => Swal.fire('Error', 'Wrong Credentials', 'error')
      complete: () => this.login() 
    })

  }

  login(){
    this.router.navigate(['login']);
  }

}
