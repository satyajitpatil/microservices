import { AuthGuardService } from './auth-guard.service';
import { AuthService } from './site/auth.service';
import { UserService } from './site/user.service';
import { CartService } from './shopping/cart/cart.service';
import { FoodService } from './food/food.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FoodItemComponent } from './food/item-info/item-info.component';
import { FoodMenuComponent } from './food/menu/menu.component';
import { FoodSearchComponent } from './food/search/search.component';
import { CartComponent } from './shopping/cart/cart.component';
import { SignupComponent } from './site/signup/signup.component';
import { LoginComponent } from './site/login/login.component';
import { FoodItemEditComponent } from './food/item-edit/item-edit.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    FoodItemComponent,
    FoodMenuComponent,
    FoodSearchComponent,
    CartComponent,
    SignupComponent,
    LoginComponent,
    FoodItemEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [FoodService, CartService, UserService, AuthService, AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
