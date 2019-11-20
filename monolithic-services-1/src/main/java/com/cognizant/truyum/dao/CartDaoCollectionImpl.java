package com.cognizant.truyum.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;
@Component
public class CartDaoCollectionImpl implements CartDao {

	private static Map<Long, Cart> userCarts;

	public CartDaoCollectionImpl() {
		if (userCarts == null) {
			userCarts = new HashMap<>();
		}
	}

	@Override
	public void addCartItem(long userId, long menuItemId) {

		MenuItemDao menuItemDao = new MenuItemDaoCollectionImpl();
		MenuItem menuItem = menuItemDao.getMenuItem(menuItemId);

		Cart cart = null;
		if (userCarts.containsKey(userId)) {
			cart = userCarts.get(userId);
			List<MenuItem> cartItems = cart.getMenuItemList();
			cartItems.add(menuItem);

			double total = cart.getTotal() + menuItem.getPrice();
			cart.setMenuItemList(cartItems);
			cart.setTotal(total);
			userCarts.put(userId, cart);

		} else {
			List<MenuItem> cartItems = new ArrayList<>();
			cartItems.add(menuItem);
			cart = new Cart(cartItems, menuItem.getPrice());
			userCarts.put(userId, cart);
		}

	}

	@Override
	public List<MenuItem> getAllCartItems(long userId) {
		Cart cart = userCarts.get(userId);
		List<MenuItem> cartItems = null;
		if (cart != null) {
			cartItems = cart.getMenuItemList();
		}
		return cartItems;
	}

	@Override
	public void removeCartItem(long userId, long menuItemId) {
		MenuItemDao menuItemDao = new MenuItemDaoCollectionImpl();
		MenuItem menuItem = menuItemDao.getMenuItem(menuItemId);
		userCarts.get(userId).getMenuItemList().remove(menuItem);

	}

}
