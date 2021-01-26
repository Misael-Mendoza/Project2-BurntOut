package com.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Company;
import com.jobportal.model.User;
import com.jobportal.model.UserRole;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public List<User> findAll();
	public User findByUsername(String username);
	public User findByEmail(String email);
	public List<User> findByUserRole(UserRole userRole);
	public List<User> findByCompanyId(Company company);
	public User findBySalt(String salt);
	public User findByUserId(int userId);
;	
}
