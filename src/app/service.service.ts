import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { userModel } from './userModel';
import { authResponse } from './authResponse';
import { foodCalorie } from './foodCalorie';
import { DatePipe } from '@angular/common';
import { userDetail } from './userDetail';
import { textDto } from './textDto';
import { foodImg } from './foodImg';
import { textManualDto } from './textManualDto';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  private baseURL="http://localhost:8080"
  private userUni : userModel | undefined
  // currentDate: string;


  constructor(private httpClient: HttpClient, private datePipe: DatePipe) {
    
   }

  registerUser(user : userModel):Observable<authResponse>{
    return this.httpClient.post<authResponse>(this.baseURL+"/auth/register", user);
  }

  loginUser(user : userModel):Observable<authResponse>{
    return this.httpClient.post<authResponse>(this.baseURL+"/auth/login", user);
  }

  // getUser(user: userModel):Observable<authResponse>{
  //   return this.httpClient.post<authResponse>(this.baseURL+"/auth/login", user);
  // }

  home(email_id: string):Observable<userModel>{
    // console.log(user);
    console.log(localStorage.getItem('token'));
    
    let headers=new HttpHeaders()
    .set("Authorization","Bearer "+localStorage.getItem('token'))
    return this.httpClient.get<userModel>(this.baseURL+"/getUserByEmail/"+email_id,{headers});
  }

  getUserDetail(user_id: number):Observable<userModel>{
    let headers=new HttpHeaders()
    .set("Authorization","Bearer "+localStorage.getItem('token'))
    return this.httpClient.get<userModel>(this.baseURL+"/getUserDetails/"+user_id,{headers});
  }

  getFoodListByDate(user_id: number, dt:String):Observable<foodCalorie[]>{
    let headers=new HttpHeaders()
    .set("Authorization","Bearer "+localStorage.getItem('token'))
    return this.httpClient.get<foodCalorie[]>(this.baseURL+"/gemini/ai/dailyfooditem/"+user_id+"/"+dt,{headers});
  }

  addDetail(user_id:number, userDet:userDetail):Observable<userDetail>{
    let headers=new HttpHeaders()
    .set("Authorization","Bearer "+localStorage.getItem('token'))
    return this.httpClient.post<userDetail>(this.baseURL+"/createUserDetails/"+user_id,userDet,{headers});
  }

  updateDetail(user_id:number, userDet:userDetail):Observable<userDetail>{
    let headers=new HttpHeaders()
    .set("Authorization","Bearer "+localStorage.getItem('token'))
    return this.httpClient.put<userDetail>(this.baseURL+"/updateUserDetails/"+user_id,userDet,{headers});
  }

  getDetail(user_id:number):Observable<userDetail>{
    let headers=new HttpHeaders()
    .set("Authorization","Bearer "+localStorage.getItem('token'))
    return this.httpClient.get<userDetail>(this.baseURL+"/getUserDetails/"+user_id,{headers});
  }

  mealDetail(user_id:number, date:String, txt:textDto):Observable<boolean>{
    let headers=new HttpHeaders()
    .set("Authorization","Bearer "+localStorage.getItem('token'))
    return this.httpClient.post<boolean>(this.baseURL+"/gemini/ai/mealDetail/"+user_id+"/"+date,txt,{headers});
  }

  mealManualDetail(userId: number, date: string, txtManual: textManualDto): Observable<any> {
    let headers=new HttpHeaders()
    .set("Authorization","Bearer "+localStorage.getItem('token'))
    return this.httpClient.post<any>(this.baseURL+"/gemini/ai/mealDetailsManual/"+userId+"/"+date, txtManual, {headers});
  }

  imgDetail(user_id:number, date:String, obj:FormData):Observable<string>{
    console.log(obj);
    
    let headers=new HttpHeaders()
    .set("Authorization","Bearer "+localStorage.getItem('token'))
    return this.httpClient.post<string>(this.baseURL+"/gemini/ai/imgdetail/"+user_id+"/"+date,obj,{headers});
  }
}
