package com.yourl.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yourl.controller.dto.AccountCreateResponse;
import com.yourl.service.AccountService;

@RestController
@RequestMapping( "/login")
public class LoginController {

	private static final Logger LOG = LoggerFactory
			.getLogger(LoginController.class);
	
	@Autowired
	private AccountService registrationService;

	@RequestMapping(value = "/{accountId}/{password}", method = RequestMethod.POST)
	public AccountCreateResponse register(
			@PathVariable String accountId,@PathVariable String password) {
		LOG.info("Request to login user with accountId:{0} ",
				accountId);
		
		Principal p;

		try {
			return registrationService.createAccount(accountId);

		} catch(Exception e) {
			LOG.error("Error while creating account: {}", e.getMessage(),
					e);
			throw new RuntimeException(e.getMessage()!=null?e.getMessage():"Error in creating account");
		}
		
	}
	
	
}
