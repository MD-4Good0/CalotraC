// 
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { ServiceService } from '../service.service';
import { userModel } from '../userModel';
import { userDetail } from '../userDetail';
import { mergeMap, switchMap } from 'rxjs/operators';
import { forkJoin } from 'rxjs';
import { foodCalorie } from '../foodCalorie';
import { ChartComponent } from "ng-apexcharts";
import {
  ApexNonAxisChartSeries,
  ApexResponsive,
  ApexChart
} from "ng-apexcharts";
import { Chart } from 'chart.js/auto';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  
})
export class HomeComponent implements OnInit{
addDinner(): void {  
  if (this.appComponent.user_id) {
    this.router.navigate(['addFood', this.appComponent.user_id, 'Dinner',this.date]); // Navigate to detail with user_id if defined
  } else {
    console.error('User ID is not defined.'); // Handle case where user_id is not defined
  }
}
addLunch(): void {  
  if (this.appComponent.user_id) {
    this.router.navigate(['addFood', this.appComponent.user_id, 'Lunch',this.date]); // Navigate to detail with user_id if defined
  } else {
    console.error('User ID is not defined.'); // Handle case where user_id is not defined
  }
}
addBreakfast(): void {  
  if (this.appComponent.user_id) {
    this.router.navigate(['addFood', this.appComponent.user_id, 'Breakfast',this.date]); // Navigate to detail with user_id if defined
  } else {
    console.error('User ID is not defined.'); // Handle case where user_id is not defined
  }
}
addSnacks(): void {  
  if (this.appComponent.user_id) {
    this.router.navigate(['addFood', this.appComponent.user_id, 'Snacks',this.date]); // Navigate to detail with user_id if defined
  } else {
    console.error('User ID is not defined.'); // Handle case where user_id is not defined
  }
}
detail() {
  this.router.navigate(['detail',this.user.user_id])
}
addMeal() : void {  
  if (this.appComponent.user_id) {
    this.router.navigate(['addFood', this.appComponent.user_id]); // Navigate to detail with user_id if defined
  } else {
    console.error('User ID is not defined.'); // Handle case where user_id is not defined
  }
}
 
  // progress = 150;
  dateFromHTML: string = new Date().toISOString().substring(0, 10)
  date: String='';
  user: userModel = new userModel();
  userDet: any;
  foodList: any;
  totCal: number=0;
  totP: number=0;
  totC: number=0;
  totF: number=0;
  totCalP:number=0;
  maxCal:number=0;
  progressMessage: string = '';
  goal:string='';
  dinnerList: foodCalorie[] = [];
  lunchList: foodCalorie[] = [];
  bfList: foodCalorie[] = [];
  snacksList: foodCalorie[] = [];
  chart:any;
  // chartOptions: { series: number[]; chart: { width: number; type: string; }; labels: string[]; responsive: { breakpoint: number; options: { chart: { width: number; }; legend: { position: string; }; }; }[]; };

  constructor(private service: ServiceService, private router: Router, 
    private appComponent:AppComponent, private route: ActivatedRoute, private datePipe: DatePipe){
      
    }
   
  
  ngOnInit(): void {
    if(localStorage.getItem('token')===null)
      this.router.navigate(['login'])
    
    this.appComponent.showNavbar=true;
    this.totCal=0;
    this.totP=0;
    this.totC=0;
    this.totF=0;
    this.totCalP=0;
    // this.maxCal=0;
    this.dinnerList=[];
    this.lunchList=[];
    this.bfList=[];
    this.snacksList=[];
    
   
    this.date = this.dateFromHTML;
    console.log(this.date);
    
    this.appComponent.user_id=this.route.snapshot.params['user_id'];
    this.service.home(localStorage.getItem('emailId') || '{}')      
    .pipe(
      switchMap((data: any) => {
        this.user = data;
        return forkJoin([
          this.service.getUserDetail(this.user.user_id),
          this.service.getFoodListByDate(this.user.user_id, this.date)
        ]);
      })
    )
    .subscribe(([userDetail, foodList]) => {
      
      this.userDet = userDetail;
      this.maxCal=this.userDet.bmr;
      this.goal=this.userDet.goal;
      this.foodList = foodList;
      foodList.forEach((foodCalorie)=>{
        this.totCal+=foodCalorie.calories;
        this.totP+=foodCalorie.protein;
        this.totF+=foodCalorie.fat;
        this.totC+=foodCalorie.carbohydrates;
        if(foodCalorie.item_type==='Dinner')
          this.dinnerList.push(foodCalorie)
        else if(foodCalorie.item_type==='Lunch')
          this.lunchList.push(foodCalorie)
        else if(foodCalorie.item_type==='Breakfast')
          this.bfList.push(foodCalorie)
        else
        this.snacksList.push(foodCalorie)
      })
      // this.chart.data.datasets.data.push(this.totP);
      // this.chart.data.datasets.data.push(this.totC);
      // this.chart.data.datasets.data.push(this.totF);

      this.chart = new Chart("MyChart", {
        type: 'doughnut', // Chart type
        
        data: { // Data for the chart
          labels: ['Proteins', 'Carbohydrates', 'Fats'],
          datasets: [{
            label: 'Quantity in g',
            data: [this.totP, this.totC, this.totF],
            backgroundColor: [
              '#8A2BE2', // Proteins
              '#FFD700', // Carbohydrates
              '#FFA07A', // Fats
            ],
            hoverOffset: 10 // Increases slice size on hover
          }]
        },
      
        // Style and configuration options
        options: {
          responsive: true,
          maintainAspectRatio: false, // Allows custom size adjustments
      
          // Layout settings
          layout: {
            padding: {
              top: 20,
              bottom: 20,
              left: 20,
              right: 20
            }
          },
      
          plugins: {
            legend: {
              display: true, // Show legend
              position: 'right', // Positions legend to the right
              labels: {
                color: '#fff', // Legend text color
                font: {
                  size: 14, // Legend font size
                },
                padding: 15 // Padding between legend items
              }
            },
            tooltip: {
              callbacks: {
                label: function(context) {
                  return '   ' + context.dataset.label + ': ' + context.raw;
                }
              },
              backgroundColor: '#444', // Tooltip background
              titleFont: {
                size: 16,
                weight: 'bold'
              },
              bodyFont: {
                size: 14
              },
              padding: 10
            }
          },
      
          // Doughnut chart-specific options
          cutout: '60%', // Adjusts the size of the inner cutout
          borderColor: '#fff', // Border color for slices
        }
      });
      
      console.log(this.totCal);
      
      this.totCalP+=(this.totCal*100)/this.maxCal;
      if(this.userDet.goal==='lose' || this.userDet.goal==='maintain'){
        if(this.totCal<this.maxCal)
          this.progressMessage='You are '+(this.maxCal-this.totCal).toFixed(2)+' calories below your daily calorie limit.'
        else if((this.totCal>this.maxCal))
          this.progressMessage='You are '+(this.totCal-this.maxCal).toFixed(2)+' calories above your daily calorie limit.'
        else
        this.progressMessage='You are at your daily calorie limit.'
      }
      else if(this.userDet.goal==='gain'){
        if(this.totCal<this.maxCal)
          this.progressMessage='You are '+(this.maxCal-this.totCal).toFixed(2)+' calories below your daily calorie goal.'
        else if((this.totCal>this.maxCal))
          this.progressMessage='You are '+(this.totCal-this.maxCal).toFixed(2)+' calories above your daily calorie goal.'
        else
        this.progressMessage='You have reached your daily calorie goal.'
      }
      
    }, (error) => {
      
      
      if(error.status===404)
        this.router.navigate(['detail', this.user.user_id]);
      // this.router.navigate(['login']);
    });
  }

  
}
