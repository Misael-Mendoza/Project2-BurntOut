package com.jobportal.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.exception.UserAlreadyExistsException;
import com.jobportal.exception.UserNotFoundException;
import com.jobportal.mail.RecoveryEmail;
import com.jobportal.model.Company;
import com.jobportal.model.User;
import com.jobportal.model.UserRole;
import com.jobportal.repository.UserRepository;
import com.jobportal.security.Encrypt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class UserService {
	
	private UserRepository userRepo;
	
	public boolean verifyUser(String username, String password) throws NoSuchAlgorithmException {
		boolean isVerified = false;
		User userCheck = userRepo.findByUsername(username);
		if(userCheck.getPassword().equals(Encrypt.generateHash(password, "SHA-256", Encrypt.hexStringToByteArray(userCheck.getSalt()))[0])) {
			isVerified = true;
		}
		return isVerified;
	}
	
	public void encryptPassword(String username, String password) throws NoSuchAlgorithmException, UserNotFoundException {
		User user = userRepo.findByUsername(username);
		byte[] salt = Encrypt.createSalt();
		String[] hashAndSalt = Encrypt.generateHash(password, "SHA-256", salt);
		user.setPassword(hashAndSalt[0]);
		user.setSalt(hashAndSalt[1]);
		updateUser(user);
	}
	
//	public String encryptSecurityCode(String code) throws NoSuchAlgorithmException {
//		String hash = Encrypt.generateHash(code, "SHA-256");
//		return hash;
//	}
//	
	public void sendRecoveryEmail(String email, String securityCode) throws UserNotFoundException {
		if(userRepo.findByEmail(email)!=null) {
			try {
				RecoveryEmail.sendRecoveryMail(email, securityCode);
			} catch(Exception e) {
				e.printStackTrace();
			} 
		} else {
			throw new UserNotFoundException();
		}
	}
	
	public int generateSecurityCode() {
		Random rand = new Random();
		int randInt = rand.nextInt(1_000_000);
		return randInt;
	}
	
	/*----------------CRUD METHODS----------------*/
	
	public void insertUser(User user) throws UserAlreadyExistsException {
		if(userRepo.findByUsername(user.getUsername()) != null) {
			throw new UserAlreadyExistsException("User with that username already exists");
		} else if(userRepo.findByEmail(user.getEmail()) != null) {
			throw new UserAlreadyExistsException("User with that email already exists");
		} else {
			userRepo.save(user);			
		}
	}
	
	public List<User> getAllUsers() {
		if(true) {	//need a test to see if current session is an administrator
			return userRepo.findAll();
		}
		return null;
	}
	
	public void updateUser(User user) throws UserNotFoundException {
		if(userRepo.findById(user.getUserId())!=null) {
			userRepo.save(user);
		} else {
			throw new UserNotFoundException("That user was not able to be found!");
		}
	}
	
	public void deleteUser(User user) throws UserNotFoundException {
		if(user!=null) {	//need to check that the user exists in the first place	
//			if() { //check if that user is the one with the current session
//				userRepo.delete(user);
//			} else if(){ //check if current session is administrator
				userRepo.delete(user);
//			}
		} else {
			System.out.println("User Does Not Exist");
			throw new UserNotFoundException();
		}
	}
	
	/*----------------GET-BY METHODS----------------*/
	
	public User getUserByUsername(String username) {
		User user = null;
		if(true) { 	
			user = userRepo.findByUsername(username);
		}
		return user;
	}
	
	public User getUserByEmail(String email) {
		User user = null;
		if(true) {	
			user = userRepo.findByEmail(email);
		}
		return user;
	}
	
	public User getUserBySecurityCode(String securityCode) {
		User user = null;
		if(securityCode.length()<15) {
			user = userRepo.findBySalt(securityCode);
		}
		return user;
	}
	
	public User getUserByUserId(int id) {
		User user = userRepo.findByUserId(id);
		return user;
	}
	
//	public List<User> getUsersByRole(UserRole userRole) {
//		List<User> userList = new ArrayList<>();
//		if(true) {	
//			userList = userRepo.findByUserRole(userRole);
//		}
//		return userList;
//	}
//	
//	public List<User> getUsersByCompany(Company company) {
//		List<User> userList = new ArrayList<>();
//		if(true) {	
//			userList = userRepo.findByCompanyId(company);
//		}
//		return userList;
//	}
	
}
