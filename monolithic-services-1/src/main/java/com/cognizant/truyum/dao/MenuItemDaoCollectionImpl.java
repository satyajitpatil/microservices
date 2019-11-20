package com.cognizant.truyum.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.cognizant.truyum.model.MenuItem;
@Component
public class MenuItemDaoCollectionImpl implements MenuItemDao {

	private static List<MenuItem> menuItemList;

	public MenuItemDaoCollectionImpl() {
		if (menuItemList == null) {
			menuItemList = new ArrayList<>();

			// Adding Sample Data
			 ApplicationContext context = new ClassPathXmlApplicationContext("truyum.xml");
			 menuItemList = context.getBean("menuItemList", ArrayList.class);

			// System.out.println(menuItemList.size());

		}
	}

	@Override
	public List<MenuItem> getMenuItemListAdmin() {
		System.out.println("ADMIN LIST");
		return menuItemList;
	}

	@Override
	public List<MenuItem> getMenuItemListCustomer() {
		System.out.println("CUSTOMER LIST");

		List<MenuItem> menuItemListCustomer = new ArrayList<>();
		Date today = new Date();
		for (MenuItem menuItem : menuItemList) {
			if ((menuItem.getDateOfLaunch().compareTo(today)) <= 0) {
				/*if (menuItem.getActive().equals("yes")) {
					menuItemListCustomer.add(menuItem);
				}*/
			}
		}
		return menuItemListCustomer;
	}

	@Override
	public MenuItem modifyMenuItem(MenuItem menuItem) {
		
		menuItemList.set((int) menuItem.getId() - 1, menuItem);
		return menuItemList.get((int)menuItem.getId()-1);
		
	}

	@Override
	public MenuItem getMenuItem(long menuItemId) {

		MenuItem menuItem = menuItemList.get((int) menuItemId - 1);

		
		return menuItem;
	}

}
