import { AuthService } from './../site/auth.service';
import { CartService } from './../shopping/cart/cart.service';
import { FoodItem } from './FoodItem';
import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  baseUrl = environment.baseUrl;
  private subject = new Subject<FoodItem[]>();
  isAdmin = false;
  addedToCart = false;
  cartAddedId: number;
  foodItemsList: FoodItem[] = [];
  isLoggedIn = false;
  clickedOnAdd = false;

  userAuthCredentials = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('user:pwd')
    })
  };
  adminAuthCredentials = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('admin:admin')
    })
  };

  constructor(private cartService: CartService, private router: Router, private http: HttpClient) {

  }

  getSubject(): Subject<FoodItem[]> {
    return this.subject;
  }

  getAllFoodItems(): Observable<FoodItem[]> {
    return this.http.get<FoodItem[]>(this.baseUrl + '/menu-items', this.isAdmin ? this.adminAuthCredentials : this.userAuthCredentials)
  }

  getFoodItems() {
    return this.foodItemsList;
  }

  getFoodItemsCustomer() {
    this.http.get<FoodItem[]>(this.baseUrl + '/menu-items').subscribe((res) => console.log(res))
    return this.foodItemsList.filter((foodItem) => foodItem.active && foodItem.dateOfLaunch <= new Date());
  }

  getFoodItem(id: number): Observable<FoodItem> {
    return this.http.get<FoodItem>(this.baseUrl + '/' + id, this.adminAuthCredentials)
  }

  addToCart(foodItemId: number) {
    if (this.isLoggedIn) {
      let userId = 1;
      this.http.post(this.baseUrl + '/carts/' + userId + '/' + foodItemId, {}, this.userAuthCredentials).subscribe(() => {
        this.addedToCart = true;
        this.cartAddedId = foodItemId;
      });

      /*  for (const foodItem of this.foodItemsList) {
         if (foodItem.id == foodItemId) {
           this.cartService.getCart().cartItems.push(foodItem);
           this.cartService.calcPrice();
           this.addedToCart = true;
           this.cartAddedId = foodItemId;
         }
       } */
    } else {
      this.clickedOnAdd = true;
      this.router.navigate(['login']);
    }
  }

  removeFromCart(foodItemId: number) {
    let userId = 1;
    this.http.delete(this.baseUrl + '/carts/' + userId + '/' + foodItemId, this.userAuthCredentials).toPromise().then(() => {
      this.cartService.getAllCartItems().toPromise().then(
        (c) => {
          this.cartService.cartItemsSubject.next(c)
          this.cartService.cart = c;
        }
      );
    });
    /*  for (let i = 0; i < this.cartService.getCart().cartItems.length; i++) {
       if (this.cartService.getCart().cartItems[i].id === foodItemId) {
         this.cartService.getCart().cartItems.splice(i, 1);
         this.cartService.calcPrice();
         break;
       }
     } */
  }



  updateFoodItem(foodItem: FoodItem) {



    this.http.put<FoodItem>(this.baseUrl + '/', foodItem, this.adminAuthCredentials).subscribe((res) => console.log(res))


    /*  let count = 0;
     for (const fItem of this.foodItemsList) {

       if (fItem.id === foodItem.id) {
         this.foodItemsList[count] = foodItem;
         break;
       }
       count++;
     }
     console.log(this.foodItemsList); */
  }

}
