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
import org.springframework.web.bind.annotation.PutMapping;
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
	
	/**
	 * Method for posting a new user to the database
	 * @param uMap (JSON object)
	 * @return 201 response if user is created, 406 if user with those details already exists
	 * @throws UserNotFoundException
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/newuser")
	public ResponseEntity<String> insertUser(@RequestBody LinkedHashMap uMap) throws UserNotFoundException {
		UserRole userRole = userRoleServ.getRoleByName((String)uMap.get("userRole"));
		Company company = compServ.getCompanyByName((String)uMap.get("company"));
		
		if(company == null) {	//if the company a user enters doesn't exist in the database, this adds it to the database
			company = new Company((String)uMap.get("company"));
			compServ.insertCompany(company);
		}
		
		User user = new User((String)uMap.get("firstName"), (String)uMap.get("lastName"), (String)uMap.get("email"), (String)uMap.get("username"), 
				(String)uMap.get("password"), null, userRole, company);

		try {
			userServ.insertUser(user);
			userServ.encryptPassword(user.getUsername(), user.getPassword()); 
		} catch(UserAlreadyExistsException e) { //occurs if username/email already exist in the database
			e.printStackTrace();
			return new ResponseEntity<>("User with those details already exists", HttpStatus.NOT_ACCEPTABLE);
		} catch(NoSuchAlgorithmException nsae) { //should never occur because the algorithm is hard-coded
			nsae.printStackTrace();
			return new ResponseEntity<>("Encryption failed for some reason", HttpStatus.NOT_ACCEPTABLE);
		}
		
		return new ResponseEntity<>("User Successfully Created!", HttpStatus.CREATED);
	}
	
	/**
	 * Method to verify a user's credentials for logging in
	 * @param uMap (JSON object)
	 * @return 200 response and the user object matching the credentials, 404 response if the user wasn't found, 406 response if verification failed
	 */
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
			return new ResponseEntity<>(userServ.getUserByUsername(username), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	/**
	 * Method to send a recovery email if the user forgot their password
	 * @param rMap (JSON object containing email)
	 * @return 200 response if the email was successfully sent, 400 if an exception was thrown
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/recover")
	public ResponseEntity<String> postRecover(@RequestBody LinkedHashMap rMap) {
		String email = (String)rMap.get("email");
		try {
			//making a security code for the recovery email and storing it in the salt column of the user's db record, therefore login will not work until the password is reset
			String securityCode = String.valueOf(userServ.generateSecurityCode());
			User resetUser = userServ.getUserByEmail(email);
			resetUser.setSalt(securityCode);
			userServ.updateUser(resetUser);
			userServ.sendRecoveryEmail(email, securityCode);
			return new ResponseEntity<>("An email has been sent with instructions to reset your password", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("A problem occurred", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Method to set the user's new password if they requested a password reset
	 * @param passMap (JSON object containing security code as 'username' and new password
	 * @return 202 response if password was successfully reset, 400 if an error occured in encryption, 404 if security code does not match any users
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/passwordreset")
	public ResponseEntity<String> putPassReset(@RequestBody LinkedHashMap passMap) {
		User user = null;
		user = userServ.getUserBySecurityCode((String)passMap.get("username"));

		if(user!=null) {
			user.setPassword((String)passMap.get("password"));
			try {
				userServ.encryptPassword(user.getUsername(), user.getPassword());
				userServ.updateUser(user);		
				return new ResponseEntity<>("Password successfully reset", HttpStatus.ACCEPTED);
			}catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>("An error has occured", HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity<>("Security Code unable to be verified", HttpStatus.NOT_FOUND);
		}
		
	}
	
	/**
	 * Returns all Users
	 * @return list of users in database
	 */
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> userList = userServ.getAllUsers();
		if(userList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(userList, HttpStatus.OK);
		}
	}
	
	/**
	 * Find user by username
	 * @param username
	 * @return user with that username
	 */
	@CrossOrigin(origins = "*")
	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username){
		User user = userServ.getUserByUsername(username);
		if(user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	/**
	 * Find user by email
	 * @param email
	 * @return user with that email
	 */
	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
		User user = userServ.getUserByEmail(email);
		if(user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	/**
	 * Updates a user's information
	 * @param hashMap (JSON object containing user's new information)
	 * @return 200 response if user successfully updated, 404 if user with that id does not exist in the database
	 */
	@CrossOrigin(origins = "*")
	@PutMapping()
	public ResponseEntity<User> putUser(@RequestBody LinkedHashMap hashMap) {
		User user = userServ.getUserByUserId((Integer)hashMap.get("id"));
		User userByUsername = userServ.getUserByUsername((String)hashMap.get("username"));
		
		if(userByUsername == null || (user.getUserId() == userByUsername.getUserId())) {
			User userByEmail = userServ.getUserByEmail((String)hashMap.get("email"));
			
			if(userByEmail == null || (user.getUserId() == userByEmail.getUserId())) {
				Company company = compServ.getCompanyByName((String)hashMap.get("company"));
				if(company == null) {
					company = new Company((String)hashMap.get("company"));
					compServ.insertCompany(company);
				}
				
				user.setFirstName((String)hashMap.get("firstName"));
				user.setLastName((String)hashMap.get("lastName"));
				user.setEmail((String)hashMap.get("email"));
				user.setUsername((String)hashMap.get("username"));
				user.setCompanyId(company);
				
				try {
					userServ.updateUser(user);
					return new ResponseEntity<>(user, HttpStatus.OK);
				} catch (UserNotFoundException e) {
					e.printStackTrace();
					return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				}
			}
			
			else {
				return new ResponseEntity<>(userByEmail, HttpStatus.METHOD_NOT_ALLOWED);
			}
		}
		
		else {
			return new ResponseEntity<>(userByUsername, HttpStatus.NOT_ACCEPTABLE);
		}
	}
}

