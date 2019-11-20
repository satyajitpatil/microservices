package com.cognizant.truyum.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cognizant.truyum.controller.AuthenticationController;
import com.cognizant.truyum.model.User;
import com.cognizant.truyum.repository.UserRepository;

@Component
public class AppUserDetailService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private UserRepository userRepository;

	AppUser appUser;
	User user;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		user = userRepository.findUserByUsername(username).get();
	System.out.println("inside APPUSERDETAILSERVICE " + user);
		if (user == null) {
			LOGGER.info("USER NOT FOUND");
			throw new UsernameNotFoundException("Username not found");
		} else {
			appUser = new AppUser(user);
		}
		System.out.println("inside APPUSERDETAILSERVICE appuser " + appUser);

		return appUser;
	}
	
	public void signUp(User user) throws Exception{
		Optional<User> userObj = userRepository.findUserByUsername(user.getUserName());
		
		if (userObj.isPresent()) {
			LOGGER.info("USER NOT FOUND");
			throw new Exception("User already exists");
			//TODO: Custom exception
		} else {
			System.out.println(user);
			String pass = user.getPassword();
			user.setPassword(passwordEncoder().encode(pass));
			//userRepository.save(user);
			userRepository.addUser(user.getUserName(), user.getFirstName(),user.getLastName(), user.getPassword());
			User newUser = userRepository.findUserByUsername(user.getUserName()).get();
			userRepository.addUserRole(newUser.getUserId(), 2);
		}
	}
	
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
