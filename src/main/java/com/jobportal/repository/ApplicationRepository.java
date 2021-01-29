package com.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Application;
import com.jobportal.model.JobPosting;
import com.jobportal.model.User;

/**
 * This interface acts as a way to query the database, it's only role is to do queries involving Applications.
 * It uses Spring Data to construct its queries
 * @author darie
 */
public interface ApplicationRepository extends JpaRepository<Application, Integer>{
	
	public List<Application> findAll();
	public Application findByApplicationId(int applicationId);
	public List<Application> findByApplicantId(User applicantId);
	public List<Application> findByPostingId(JobPosting postingId);

}
