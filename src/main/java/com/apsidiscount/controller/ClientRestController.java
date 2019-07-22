package com.apsidiscount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apsidiscount.entity.Client;
import com.apsidiscount.exceptions.LoginAndPasswordException;
import com.apsidiscount.service.ClientService;

@CrossOrigin
@RestController
public class ClientRestController {

	
	@Autowired
	private ClientService clientService;
	

	@PostMapping(consumes="application/json", produces="application/json", path="/api/client")
	public Client getClientByLoginAndPassword(@RequestBody ClientDto jsonLogin) throws LoginAndPasswordException {
		String email = jsonLogin.getEmail();
		String password = jsonLogin.getPassword();
		Client client = clientService.getClientByNameAndPassword(email, password);
		return client;
	}
}
