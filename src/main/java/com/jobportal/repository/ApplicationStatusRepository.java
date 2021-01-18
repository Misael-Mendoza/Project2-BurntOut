package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.ApplicationStatus;

public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Integer>{

	public ApplicationStatus findBystatusId(int statusId);
	public ApplicationStatus findBystatus(String status);
}
