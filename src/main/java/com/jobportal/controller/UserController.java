package com.jobportal.controller;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.exception.UserAlreadyExistsException;
import com.jobportal.exception.UserNotFoundException;
import com.jobportal.model.Company;
import com.jobportal.model.User;
import com.jobportal.model.UserRole;
import com.jobportal.service.CompanyService;
import com.jobportal.service.UserRoleService;
import com.jobportal.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value="/users")
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class UserController {
	
	private UserService userServ;
	private UserRoleService userRoleServ;
	private CompanyService compServ;
	
	@CrossOrigin(origins = "*")
	@PostMapping("/newuser")
	public ResponseEntity<String> insertUser(@RequestBody LinkedHashMap uMap) throws UserNotFoundException {
		UserRole userRole = userRoleServ.getRoleByName((String)uMap.get("userRole"));
		Company company = compServ.getCompanyByName((String)uMap.get("company"));
		User user = new User((String)uMap.get("firstName"), (String)uMap.get("lastName"), (String)uMap.get("email"), (String)uMap.get("username"), 
				(String)uMap.get("password"), null, userRole, company);
		System.out.println("User: " + user);

		try {
			userServ.insertUser(user);
			userServ.encryptPassword(user.getUsername(), user.getPassword());
		} catch(UserAlreadyExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<>("User with those details already exists", HttpStatus.NOT_ACCEPTABLE);
		} catch(NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
			return new ResponseEntity<>("Encryption failed for some reason", HttpStatus.NOT_ACCEPTABLE);
		}
		
		return new ResponseEntity<>("User Successfully Created!", HttpStatus.CREATED);
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/login")
	public ResponseEntity<User> postLogin(@RequestBody LinkedHashMap uMap) {
		String username = (String)uMap.get("username");
		String password = (String)uMap.get("password");
		
		boolean isVerified = false;
		
		if(userServ.getUserByUsername(username)==null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		try {
			isVerified = userServ.verifyUser(username, password);
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(isVerified) {
			//Logic for setting session
			System.out.println("you did it");
			return new ResponseEntity<>(userServ.getUserByUsername(username), HttpStatus.OK);
		} else {
			System.out.println("you kinda did it");
			return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
		}
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
	
	@GetMapping("/userRole/{userRoleId}")
	public ResponseEntity<List<User>> getUsersByUserRole(@PathVariable("userRoleId") int userRoleId) {
		UserRole userRole = userRoleServ.getRoleById(userRoleId);
		List<User> userList = userServ.getUsersByRole(userRole);
		if(userList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(userList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/company/{companyId}")
	public ResponseEntity<List<User>> getUsersByCompany(@PathVariable("companyId") int companyId) {
		Company company = compServ.getCompanyById(companyId);
		List<User> userList = userServ.getUsersByCompany(company);
		if(userList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(userList, HttpStatus.OK);
		}
	}
	
}

