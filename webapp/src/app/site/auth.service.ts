import { FoodService } from './../food/food.service';
import { Injectable } from '@angular/core';
import { User } from './User';
import { UserService } from './user.service';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedInUser: User;
  redirectUrl = '/';
  loggedIn: boolean = false;
  name: string;
  validCredentials: boolean = false;

  cartMenuItemId: number = 0;

  constructor(private userService: UserService, private foodService: FoodService, public router: Router, private http: HttpClient) { }

  baseUrl = environment.baseUrl;
  private authenticationApiUrl = this.baseUrl + '/authenticate';
  private token: string;


  authenticate(user: string, password: string): Observable<any> {
    let credentials = btoa(user + ':' + password)
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Basic ' + credentials);
    return this.http.get(this.authenticationApiUrl, { headers })
  }

  public setToken(token: string) {
    this.token = token;
  }
  public getToken() {
    return this.token;
  }


 

  logout() {
    this.loggedInUser = null;
    this.foodService.isAdmin = false;
    this.loggedIn = false;
    this.foodService.isLoggedIn = false;
    this.foodService.clickedOnAdd = false;
    this.foodService.addedToCart = false;
    this.router.navigate(['login']);
  }
}
