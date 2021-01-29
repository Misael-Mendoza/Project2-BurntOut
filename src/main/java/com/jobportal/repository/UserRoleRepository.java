package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.UserRole;
/**
 * This interface acts as a way to query the database, it's only role is to do queries involving UserRoles.
 * It uses Spring Data to construct its queries
 * @author darie
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
	
	public UserRole findByUserRoleID(int userRoleID);
	public UserRole findByUserRole(String userRole);
	
}
