import { Time } from "@angular/common";

export class foodCalorie{
    food_id: number | undefined;
    item_type: string ='';
    date: Date  | undefined;
    time: Time | undefined;
    item_name: string = '';
    quantity: number =0;
    calories: number =0;
    protein: number =0;
    carbohydrates: number =0;
    fat: number =0;
}