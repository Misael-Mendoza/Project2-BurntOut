package com.jobportal.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.exception.UserAlreadyExistsException;
import com.jobportal.model.Company;
import com.jobportal.model.User;
import com.jobportal.model.UserRole;
import com.jobportal.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value="/users")
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class UserController {
	
	private UserService userServ;
	
	@PostMapping()
	public ResponseEntity<String> insertUser(@RequestBody LinkedHashMap uMap) {
		User user = new User((String)uMap.get("firstName"), (String)uMap.get("lastName"), (String)uMap.get("email"), (String)uMap.get("username"), 
				(String)uMap.get("password"), (String)uMap.get("salt"), (UserRole)uMap.get("userRole"), (Company)uMap.get("companyId"));
		try {
			userServ.encryptPassword(user.getUsername(), user.getPassword());
			userServ.insertUser(user);
		} catch(UserAlreadyExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<>("User with those details already exists", HttpStatus.NOT_ACCEPTABLE);
		} catch(NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
			return new ResponseEntity<>("Encryption failed for some reason", HttpStatus.NOT_ACCEPTABLE);
		}
		
		return new ResponseEntity<>("User Successfully Created!", HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> userList = userServ.getAllUsers();
		if(userList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(userList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username){
		User user = userServ.getUserByUsername(username);
		if(user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
		User user = userServ.getUserByEmail(email);
		if(user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	@GetMapping("/userRole/{userRole}")
	public ResponseEntity<List<User>> getUsersByUserRole(@PathVariable("userRole") UserRole userRole) {
		List<User> userList = userServ.getUsersByRole(userRole);
		if(userList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(userList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/company/{company}")
	public ResponseEntity<List<User>> getUsersByUserRole(@PathVariable("company") Company company) {
		List<User> userList = userServ.getUsersByCompany(company);
		if(userList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(userList, HttpStatus.OK);
		}
	}
	
}
