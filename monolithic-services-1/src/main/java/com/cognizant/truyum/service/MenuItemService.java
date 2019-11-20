package com.cognizant.truyum.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.repository.MenuItemRepository;

@Component
public class MenuItemService {

	/*
	 * @Autowired private MenuItemDaoCollectionImpl menuItemDao;
	 */
	@Autowired
	private MenuItemRepository menuItemRepository;

	public MenuItemService() {

	}

	public Set<MenuItem> getMenuItemListCustomer() {
		System.out.println(menuItemRepository.getMenuItemListCustomer());
		return menuItemRepository.getMenuItemListCustomer();
	}

	public Set<MenuItem> getMenuItemListAdmin() {
		return menuItemRepository.getMenuItemListAdmin();
	}

	public MenuItem getMenuItem(long id) {
		return menuItemRepository.getMenuItem(id);
	}

	public MenuItem modifyMenuItem(MenuItem m) {
		int r = menuItemRepository.modifyMenuItem(m.getName(), m.getPrice(), m.getActive(),
					m.getDateOfLaunch(), m.getCategory(), m.getFreeDelivery(), m.getImageURL(), m.getId());
		if(r>0){
			return menuItemRepository.getMenuItem(m.getId());
		}
		return m;

	}

}
