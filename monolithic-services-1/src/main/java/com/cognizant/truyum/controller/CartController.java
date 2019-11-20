package com.cognizant.truyum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.truyum.dto.CartDTO;
import com.cognizant.truyum.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

	@Autowired
	private CartService cartService;

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	@PostMapping("/{userId}/{menuItemId}")
	public void addCartItem(@PathVariable("userId") long userId, @PathVariable("menuItemId") long menuItemId) {
		cartService.addCartItem(userId, menuItemId);
	}

	@GetMapping("/{userId}")
	public CartDTO getAllCartItems(@PathVariable("userId") long userId) {
		CartDTO cartDTO = new CartDTO();
		cartDTO.setCartItems(cartService.getAllCartItems(userId));
		cartDTO.setTotal(cartService.getTotal(userId));
		return cartDTO;
	}

	@DeleteMapping("/{userId}/{menuItemId}")
	public void deleteCartItem(@PathVariable("userId") long userId, @PathVariable("menuItemId") long menuItemId) {
		cartService.deleteMenuItem(userId, menuItemId);
	}

}
