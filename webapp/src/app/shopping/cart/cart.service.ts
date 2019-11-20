import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { environment } from './../../../environments/environment.prod';
import { FoodItem } from './../../food/FoodItem';
import { AuthService } from './../../site/auth.service';
import { FoodService } from './../../food/food.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Cart } from './Cart';
import { Injectable, Output, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  baseUrl = environment.baseUrl;

  @Output() cartUpdated = new EventEmitter();
  cart: Cart = { cartItems: [], total: 0 };
  cartItemsSubject: BehaviorSubject<Cart> = new BehaviorSubject({cartItems:[],total:0});
  cartItems: FoodItem[] = [];
  total: number = 0;

  authCredentials = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('user:pwd')
    })
  };

  constructor(private http: HttpClient) {

  }


  setCartItems(cartItemsList: FoodItem[]) {
    this.cart.cartItems = cartItemsList;
  }

  getAllCartItems():Observable<Cart> {
    let userId = 1;
    return this.http.get<Cart>(this.baseUrl + '/carts/' + userId, this.authCredentials);
  }

  getCartItems() {
    return this.cart.cartItems;
  }

  getCart() {
    return this.cart;
  }

  

}
