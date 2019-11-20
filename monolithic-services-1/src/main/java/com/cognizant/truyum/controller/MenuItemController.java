package com.cognizant.truyum.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.service.AppUserDetailService;
import com.cognizant.truyum.service.MenuItemService;

@RestController
public class MenuItemController {

	@Autowired
	private MenuItemService menuItemService;
	@Autowired
	private AppUserDetailService appUserDetailService;

	public MenuItemService getMenuItemService() {
		return menuItemService;
	}

	public void setMenuItemService(MenuItemService menuItemService) {
		this.menuItemService = menuItemService;
	}

	@RequestMapping(value = "/menu-items")
	public ResponseEntity<Set<MenuItem>> getAllMenuItems() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String user = authentication.getName();
		UserDetails userDetails = appUserDetailService.loadUserByUsername(user);
		String role = userDetails.getAuthorities().toArray()[0].toString();
		System.out.println(role);

		
		if (role.startsWith("ROLE_ADMIN")) {
			System.out.println("GET ADMIN LIST");

			return new ResponseEntity<>(menuItemService.getMenuItemListAdmin(), HttpStatus.OK);

		} else {
			System.out.println("GET CUSTOMER LIST");

			return new ResponseEntity<>(menuItemService.getMenuItemListCustomer(), HttpStatus.OK);

		}

	}

	@GetMapping("/{id}")
	public MenuItem getMenuItem(@PathVariable("id") long id) {
		return menuItemService.getMenuItem(id);
	}

	@PutMapping()
	public MenuItem modifyMenuItem(@RequestBody MenuItem menuItem) {
		return menuItemService.modifyMenuItem(menuItem);
	}

}
