import { Component, OnInit } from '@angular/core';
import { foodImg } from '../foodImg';
import { AppComponent } from '../app.component';
import { Router, ActivatedRoute } from '@angular/router';
import { ServiceService } from '../service.service';
import { textDto } from '../textDto';
import Swal from 'sweetalert2';
import { HomeComponent } from '../home/home.component';
import { textManualDto } from '../textManualDto';

@Component({
  selector: 'app-img-upload-det',
  templateUrl: './food-detail.component.html',
  styleUrl: './food-detail.component.css',
})
export class FoodDetailComponent implements OnInit {
  foodI: foodImg = new foodImg();
  user_id: number=0;
  selectedFile: File | undefined;
  type: string | Blob ='';
  formData: FormData = new FormData();
  txt: textDto = new textDto;
  txtManual: textManualDto = new textManualDto();
  loading: boolean = false;
  date: string='';

  constructor(private service: ServiceService, 
    private router: Router, 
    private route: ActivatedRoute,
    private appComponent:AppComponent){}

  ngOnInit(): void {
      this.user_id=this.route.snapshot.params['user_id'];
      this.type=this.route.snapshot.params['type'];
      this.date=this.route.snapshot.params['date'];
      if(localStorage.getItem('token')===null)
        this.router.navigate(['login'])

  }

  onSubmit(): void {
    // Invoke the service function to upload the image
    this.loading = true;
    if (this.selectedFile) {
      this.formData.append('img', this.selectedFile);
      this.formData.append('type',this.type ); // Assuming type is hardcoded for demonstration

      this.service.imgDetail(this.user_id, this.date, this.formData)
      .subscribe({
        next: (v) => {
          // Hide loader modal
          this.loading = false;
      },
        error: (e) => {this.router.navigate(['home',this.user_id]),
        this.loading = false;
         },
        complete: () => this.router.navigate(['home',this.user_id]) 
      })
    }
  }

  onMealSubmit(): void{
    this.loading = true;
    this.txt.type=this.route.snapshot.params['type'];
    this.service.mealDetail(this.user_id, this.date, this.txt)
      .subscribe({
        next: (v) =>{
          this.loading = false;
          if(v===false)
            {Swal.fire('Error', 'ID not accessible', 'error')
          }
          // this.prov=v;
          //  Swal.fire('Registered', 'RP ID: '+this.prov.rpID, 'success')},
          console.log(v)},
          error: (e) => {this.router.navigate(['home',this.user_id]),
          this.loading = false;
           },
        complete: () => this.router.navigate(['home',this.user_id]) 
      })
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    this.selectedFile = event.target.files[0];
  }

  onManualSubmit(): void {
    this.loading = true;
    this.txtManual.item_type = this.route.snapshot.params['type'];  // Ensure meal type is included
    console.log("This is here");
  
    // Perform form validation (optional)
    if (
      this.txtManual.item_name &&
      this.txtManual.qty != null && this.txtManual.qty > 0 &&
      this.txtManual.calories != null && this.txtManual.calories > 0 &&
      this.txtManual.protein != null && this.txtManual.protein >= 0 &&
      this.txtManual.carbohydrates != null && this.txtManual.carbohydrates >= 0 &&
      this.txtManual.fat != null && this.txtManual.fat >= 0
    ) {
      console.log("This is there");
      // Send data to the service for submission
      this.service.mealManualDetail(this.user_id, this.date, this.txtManual).subscribe({
        next: (v) => {
          this.loading = false;
          if (v === false) {
            Swal.fire('Error', 'Meal logging failed. Try again.', 'error');
          } else {
            Swal.fire('Meal Logged', 'Your meal has been logged successfully!', 'success');
          }
        },
        error: (e) => {
          this.router.navigate(['home', this.user_id]);
          this.loading = false;
        },
        complete: () => this.router.navigate(['home', this.user_id]),
      });
    } else {
      this.loading = false;
      Swal.fire('Error', 'Please fill in all required fields.', 'error');
    }
  }
  
}
