package com.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Application;
import com.jobportal.model.JobPosting;
import com.jobportal.model.User;

public interface ApplicationRepository extends JpaRepository<Application, Integer>{
	
	public List<Application> findAll();
	public Application findByApplicationId(int applicationId);
	public List<Application> findByApplicantId(User applicantId);
	public List<Application> findByPostingId(JobPosting postingId);

}
