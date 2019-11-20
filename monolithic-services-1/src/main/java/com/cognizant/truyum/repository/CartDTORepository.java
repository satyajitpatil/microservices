package com.cognizant.truyum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.truyum.model.MenuItem;

public interface CartDTORepository extends JpaRepository<MenuItem, Long> {

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO cart (ct_us_id, ct_pr_id) values(?,?);", nativeQuery = true)
	int addItemToCart(long userID, long productId);

	@Query(value = "SELECT m.me_id,m.me_name,m.me_price,m.me_active,m.me_date_of_launch,"
			+ "m.me_category,m.me_free_delivery,m.me_image_url FROM menu_item m ,"
			+ "cart c WHERE c.ct_us_id=? AND m.me_id = c.ct_pr_id;", nativeQuery = true)
	List<MenuItem> getAllCartItems(long userId);

	@Query(value = "select sum(m.me_price) from menu_item as m,cart as c where "
			+ "c.ct_us_id=? and m.me_id=c.ct_pr_id;", nativeQuery = true)
	Double getTotal(long userId);

	@Modifying
	@Transactional
	@Query(value = "DELETE from cart WHERE ct_us_id = ? AND ct_pr_id = ? LIMIT 1", nativeQuery = true)
	Integer
	
	
	deleteCartItem(long userId, long menuItemId);

}
