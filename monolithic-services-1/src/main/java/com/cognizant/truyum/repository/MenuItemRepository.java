package com.cognizant.truyum.repository;

import java.util.Date;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cognizant.truyum.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

	@Query(value = "SELECT me_id,me_name,me_price,me_active,me_date_of_launch,me_category,me_free_delivery,me_image_url "
			+ "FROM menu_item where me_active='true' and me_date_of_launch<=curdate();", nativeQuery = true)
	Set<MenuItem> getMenuItemListCustomer();

	@Query(value = "SELECT me_id,me_name,me_price,me_active,me_date_of_launch,me_category,me_free_delivery,me_image_url FROM menu_item", nativeQuery = true)
	Set<MenuItem> getMenuItemListAdmin();

	@Query(value = "SELECT me_id,me_name,me_price,me_active,me_date_of_launch,me_category,me_free_delivery,me_image_url FROM menu_item WHERE me_id = ?", nativeQuery = true)
	MenuItem getMenuItem(long id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE menu_item SET me_name=?,me_price=?,me_active=?,me_date_of_launch=?,me_category=?,me_free_delivery=?,me_image_url =? WHERE me_id=?", nativeQuery = true)
	int modifyMenuItem(String name, float price, boolean active, Date dol, String category,
			boolean freeDelivery, String imageUrl, long id);
}
