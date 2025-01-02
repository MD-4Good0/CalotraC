import { Component } from '@angular/core';
import { userDetail } from '../userDetail';
import { ServiceService } from '../service.service';
import { ActivatedRoute,Router } from '@angular/router';
import { userModel } from '../userModel';
import { AppComponent } from '../app.component';
import Swal from 'sweetalert2';
import { loadZone } from 'zone.js/lib/zone';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrl: './detail.component.css'
})
export class DetailComponent {

  userDet: userDetail=new userDetail();
  user_id: number=0;
  f:number=0;
  text:string="Edit your information";

  constructor(private service: ServiceService, private router: Router, private route: ActivatedRoute, 
    private appComponent:AppComponent){}

  ngOnInit(): void {
    if(localStorage.getItem('token')===null)
      this.router.navigate(['login'])
    this.user_id=this.route.snapshot.params['user_id'];
    this.appComponent.user_id=this.route.snapshot.params['user_id'];
    this.service.getDetail(this.user_id).subscribe({
      next: (v) =>{
       if(v===null)
        {Swal.fire('Error', 'ID not accessible', 'error')
        }
        // this.prov=v;
        //  Swal.fire('Registered', 'RP ID: '+this.prov.rpID, 'success')},
        this.userDet=v;
        this.appComponent.user_id=this.route.snapshot.params['user_id'];
        console.log(this.userDet)},
      error: (e) => {
        if(e.status!==404)
        Swal.fire('Error', 'Wrong Credentials', 'error')
      else
        {this.f=1;
          this.text="Enter your information"}
      console.log(this.f);
      
        },
      complete: () => {
        if(this.f===1)
          // {this.f=1;
            {console.log("hi");
            
            this.text="Enter your information"}
          
        else
        this.text="Edit your information"
      }
    })
    
  }

  onSubmit(){
    console.log(this.userDet);
    if(this.f===1)
      this.regDet();
    else
      this.editDet();
    
  }

  editDet(){
    this.service.updateDetail(this.user_id,this.userDet)
    .subscribe({
      next: (v) =>{
        if(v===null)
          {Swal.fire('Error', 'ID not accessible', 'error')
          }
          Swal.fire('Saved', 'Data has been updated', 'success')
        // this.prov=v;
        //  Swal.fire('Registered', 'RP ID: '+this.prov.rpID, 'success')},
        console.log(this.userDet)},
      // error: (e) => Swal.fire('Error', 'Wrong Credentials', 'error')
      // complete: () => this.router.navigate(['provider-home']) 
    })
  }

  regDet(){
    this.service.addDetail(this.user_id,this.userDet)
    .subscribe({
      next: (v) =>{
        Swal.fire('Saved', 'Data has been updated', 'success')
        // if(v===null)
        //   {Swal.fire('Error', 'ID not accessible', 'error')
        //   }
        // this.prov=v;
        //  Swal.fire('Registered', 'RP ID: '+this.prov.rpID, 'success')},
        console.log(this.userDet)},
      // error: (e) => Swal.fire('Error', 'Wrong Credentials', 'error')
      // complete: () => this.router.navigate(['provider-home']) 
    })

  }

}
