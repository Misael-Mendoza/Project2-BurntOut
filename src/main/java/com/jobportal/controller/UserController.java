package com.jobportal.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
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

import com.jobportal.Project2Application;
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

/**
 * This class act as a RESTFul controller that exposes endpoints for websites to use involving Users.
 * The only role of this class is prepare data for the DAO and service layer
 * @author darie
 */
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
		
		if(company == null) {
			company = new Company((String)uMap.get("company"));
			compServ.insertCompany(company);
		}
		
		User user = new User((String)uMap.get("firstName"), (String)uMap.get("lastName"), (String)uMap.get("email"), (String)uMap.get("username"), 
				(String)uMap.get("password"), null, userRole, company);

		try {
			userServ.insertUser(user);
			userServ.encryptPassword(user.getUsername(), user.getPassword());
		} catch(UserAlreadyExistsException e) {
			e.printStackTrace();
			Project2Application.log.info("[insertUser] New user creation attempt, but user with the information already exists");
			return new ResponseEntity<>("User with those details already exists", HttpStatus.NOT_ACCEPTABLE);
		} catch(NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
			Project2Application.log.info("[insertUser] Encryption failed for new user, could not created");
			return new ResponseEntity<>("Encryption failed for some reason", HttpStatus.NOT_ACCEPTABLE);
		}
		
		Project2Application.log.info("[insertUser] New user created");
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
			Project2Application.log.info("[postLogin] User login attempt, verification failed");
			e.printStackTrace();
		}
		if(isVerified) {
			Project2Application.log.info("[postLogin] User logged in");
			return new ResponseEntity<>(userServ.getUserByUsername(username), HttpStatus.OK);
		} else {
			Project2Application.log.info("[postLogin] User login attempt, invalid credentials");
			return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/recover")
	public ResponseEntity<String> postRecover(@RequestBody LinkedHashMap rMap) {
		String email = (String)rMap.get("email");
		try {
			String securityCode = String.valueOf(userServ.generateSecurityCode());
			User resetUser = userServ.getUserByEmail(email);
			resetUser.setSalt(securityCode);
			userServ.updateUser(resetUser);
			userServ.sendRecoveryEmail(email, securityCode);
			Project2Application.log.info("[postRecover] Recovery email sent to user");
			return new ResponseEntity<>("An email has been sent with instructions to reset your password", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			Project2Application.log.info("[postRecover] Recovery email could not be sent to user");
			return new ResponseEntity<>("A problem occurred", HttpStatus.BAD_REQUEST);
		}
	}
	
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
				Project2Application.log.info("[putPassReset] Password was reset for a user");
				return new ResponseEntity<>("Password successfully reset", HttpStatus.ACCEPTED);
			}catch(Exception e) {
				e.printStackTrace();
				Project2Application.log.info("[putPassReset] Error when resetting password");
				return new ResponseEntity<>("An error has occured", HttpStatus.BAD_REQUEST);
			}
		}else {
			Project2Application.log.info("[putPassReset] Incorrect security code input for password reset");
			return new ResponseEntity<>("Security Code unable to be verified", HttpStatus.NOT_FOUND);
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
	
	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
		User user = userServ.getUserByEmail(email);
		if(user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	
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
					Project2Application.log.info("[putUser] User information updated successfully");
					return new ResponseEntity<>(user, HttpStatus.OK);
				} catch (UserNotFoundException e) {
					e.printStackTrace();
					Project2Application.log.info("[putUser] User information unsuccessfully updated");
					return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				}
			}
			
			else {
				Project2Application.log.info("[putUser] User information update attempt: same email as another user");
				return new ResponseEntity<>(userByEmail, HttpStatus.METHOD_NOT_ALLOWED);
			}
		}
		
		else {
			Project2Application.log.info("[putUser] User information update attempt: same username as another user");
			return new ResponseEntity<>(userByUsername, HttpStatus.NOT_ACCEPTABLE);
		}
	}
}

