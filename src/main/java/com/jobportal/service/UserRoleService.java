package com.jobportal.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Location;
import com.jobportal.model.UserRole;
import com.jobportal.repository.UserRoleRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * This service class of UserRole serves as an intermediate layer between the DAO layer and the controller layer.
 * It is ideally used to enforce business rules and ensuring that the controllers only job is to prepare data and send it to the dao, 
 * while the daos layer only responsibility is to query the database and return data.
 * @author Darien Sosa
 */

@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class UserRoleService {
	
	private UserRoleRepository userRoleRepo;
	
	public UserRole getRoleById(int userRoleID) {
		UserRole userRole = userRoleRepo.findByUserRoleID(userRoleID);
		if(userRole != null) {
			return userRole;
		}else {
			return null;
		}
	}
	
	public UserRole getRoleByName(String userRole) {
		UserRole uR = userRoleRepo.findByUserRole(userRole);
		if(uR != null) {
			return uR;
		}else {
			return null;
		}
	}
	
}
