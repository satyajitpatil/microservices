import { FoodService } from './../../food/food.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { AuthenticationService } from 'src/app/login/authentication.service';
import { catchError } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = '';
  password = '';

  loginForm: FormGroup;
  submitted = false;

  validCredentials = true;
  successLogin = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService, private foodService: FoodService, private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      'username': new FormControl(this.username, [Validators.required, Validators.minLength(2)]),
      'password': new FormControl(this.password, [Validators.required, Validators.minLength(2)]),
    });

    console.log(this.foodService.clickedOnAdd);
  }

  get f() {
    return this.loginForm.controls;
  }

  async onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    console.log(this.loginForm.value)

    await this.authService.authenticate(this.loginForm.value.username, this.loginForm.value.password).toPromise().then((res) => {
      this.successLogin = true;
      this.authService.setToken(res.token);
      this.foodService.isAdmin = false;
      this.authService.loggedIn = true;
      this.foodService.isLoggedIn = true;
      this.authService.name = this.loginForm.value.username;
      if (res.role === 'ROLE_ADMIN') {
        this.foodService.isAdmin = true;
      } else {
        this.foodService.isAdmin = false;
      }
      console.log('Login successful')
      console.log('Add To Cart Clicked for ' + this.authService.cartMenuItemId)
      if (this.authService.cartMenuItemId !== 0) {
        console.log('Add To Cart Clicked for ' + this.authService.cartMenuItemId)

        this.foodService.addToCart(this.authService.cartMenuItemId);
        this.authService.cartMenuItemId = 0;
      }
      this.router.navigateByUrl('');
      this.validCredentials = true;
      this.router.navigate(['']);
    }, () => { this.successLogin = false; this.validCredentials = false; }
    );



  }

  private handleError(error: HttpErrorResponse) {
    this.successLogin = false;
    return null;
  }


}
