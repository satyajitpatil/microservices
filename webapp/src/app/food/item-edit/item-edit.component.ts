import { FoodItem } from './../FoodItem';
import { FoodService } from './../food.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-food-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.css']
})
export class FoodItemEditComponent implements OnInit {

  saved: boolean = false;

  foodItem: FoodItem = {
    id: 0,
    name: 'name',
    category: 'category',
    price: 0,
    freeDelivery: false,
    dateOfLaunch: new Date(),
    active: false,
    imageURL: 'string',
  };

  itemName = '';
  price = 0;
  dateOfLaunch = new Date().toISOString().substring(0, 10);
  category = 'Main Course';
  active = false;
  freeDelivery = false;
  imageUrl = '';

  editForm: FormGroup = this.formBuilder.group({
    'itemName': new FormControl(),
    'imageUrl': new FormControl(),
    'price': new FormControl(),
    'dateOfLaunch': new FormControl(),
    'category': new FormControl(),
    'active': new FormControl(),
    'freeDelivery': new FormControl(),
  });

  submitted = false;

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private router: Router, private foodService: FoodService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.foodService.getFoodItem(+params.get('id')).subscribe(
        (f) => {
          this.foodItem = f;
          console.log(f)
          this.editForm = this.formBuilder.group({
            'itemName': new FormControl(this.foodItem.name, [Validators.required, Validators.maxLength(200)]),
            'imageUrl': new FormControl(this.foodItem.imageURL, [Validators.required]),
            'price': new FormControl(this.foodItem.price, [Validators.required, Validators.pattern("^[0-9]*$"),
            ]),
            'dateOfLaunch': new FormControl(this.foodItem.dateOfLaunch.toString().substring(0, 10), [Validators.required]),
            'category': new FormControl(this.foodItem.category, [Validators.required]),
            'active': new FormControl(this.foodItem.active.toString(), [Validators.required,]),
            'freeDelivery': new FormControl(this.foodItem.freeDelivery),

          })
        })
    });


  }

  get f() {
    return this.editForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.editForm.invalid) {
      return;
    }

    let foodItemUpdated: FoodItem = {
      id: this.foodItem.id,
      name: this.editForm.value['itemName'],
      price: this.editForm.value['price'],
      dateOfLaunch: new Date(this.editForm.value['dateOfLaunch']),
      category: this.editForm.value['category'],
      active: (this.editForm.value['active'] === "true"),
      freeDelivery: this.editForm.value['freeDelivery'],
      imageURL: this.editForm.value['imageUrl']
    };

    this.foodService.updateFoodItem(foodItemUpdated)
    this.saved = true;
    console.log(this.editForm.value)
  }

}
