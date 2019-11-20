package com.cognizant.truyum.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.model.User;
import com.cognizant.truyum.service.AppUserDetailService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private AppUserDetailService appUserDetailService;

	@PostMapping()
	public void signup(@RequestBody User user) {
		try {
			appUserDetailService.signUp(user);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exists");
		}
		/*if (inMemoryUserDetailsManager.userExists(user.getUserName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exists");
		} else {
			inMemoryUserDetailsManager
					.createUser(org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
							.password(passwordEncoder().encode(user.getPassword())).roles("USER").build());
		}*/
	}
	
	/*@GetMapping()
	public void getAllUsers(){
		Field usersMapField = ReflectionUtils.findField(InMemoryUserDetailsManager.class, "users");
        ReflectionUtils.makeAccessible(usersMapField);
        Map map = (Map)ReflectionUtils.getField(usersMapField, inMemoryUserDetailsManager);
        System.out.println(map);
	}*/
	
	

	public PasswordEncoder passwordEncoder() {
		// LOGGER.info("Start");
		return new BCryptPasswordEncoder();
	}

}
