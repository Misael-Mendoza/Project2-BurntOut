package com.jobportal.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Location;
import com.jobportal.model.UserRole;
import com.jobportal.repository.UserRoleRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
