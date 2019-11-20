import { FoodService } from './../../food/food.service';
import { FoodItem } from './../../food/FoodItem';
import { CartService } from './cart.service';
import { Cart } from './Cart';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cart: Cart = { cartItems: [], total: 0 };
  constructor(private cartService: CartService, private foodService: FoodService) {
  }

  ngOnInit() {
    this.cartService.getAllCartItems().toPromise().then(
      (c) => {
        console.log(c);
        this.cart = c;
      }
    );

    this.cartService.cartItemsSubject.subscribe((c) => {
      this.cart = c
    })


    console.log(this.cart);

  }

}
