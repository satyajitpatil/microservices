package com.cognizant.truyum.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthenticationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@GetMapping("/authenticate")
	public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {

		LOGGER.info("start");
		LOGGER.info(authHeader);
		Map<String, String> authmap = new HashMap<String, String>();
		// authmap.put(generateJwt(getUser(authHeader)),getUser(authHeader));
		authmap.put("token", generateJwt(getUser(authHeader)));
		String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
		authmap.put("role", role);
		LOGGER.info("END OF AUTH FUNCTION");
		return authmap;

	}

	private String getUser(String authHeader) {
		LOGGER.info("Start");
		LOGGER.debug(authHeader);
		String encodedCredentials = authHeader.substring(6, authHeader.length());

		LOGGER.debug(encodedCredentials);
		byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
		String decodedCredentials = new String(decodedBytes);

		String user = decodedCredentials.substring(0, decodedCredentials.indexOf(':'));
		LOGGER.debug(user);
		LOGGER.info("End");
		return user;
	
		
	}

	private String generateJwt(String user) {
		LOGGER.info("Start");

		JwtBuilder builder = Jwts.builder();
		builder.setSubject(user);
		// Set the token issue time as current time
		builder.setIssuedAt(new Date());
		// Set the token expiry as 20 minutes from now
		builder.setExpiration(new Date((new Date()).getTime() + 1000));
		builder.signWith(SignatureAlgorithm.HS256, "secretkey");
		String token = builder.compact();
		LOGGER.debug(token);

		LOGGER.info("End");
		return token;
	}

}
