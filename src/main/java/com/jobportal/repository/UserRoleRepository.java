package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
	
	public UserRole findByUserRoleID(int userRoleID);
	
}
