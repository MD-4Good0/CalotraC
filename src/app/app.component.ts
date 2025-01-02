import { Component } from '@angular/core';
import { userModel } from './userModel';
import { foodCalorie } from './foodCalorie';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'CalorieApp';

  user: userModel = new userModel;
  user_id:number=0;
  foodList: foodCalorie[] = [];
  showNavbar: boolean = true;

  public child($event:any): void{
    console.log($event);
  }

}
