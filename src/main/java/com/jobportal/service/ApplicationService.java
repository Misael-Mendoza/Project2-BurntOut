package com.jobportal.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Application;
import com.jobportal.model.JobPosting;
import com.jobportal.model.User;
import com.jobportal.repository.ApplicationRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * This service class of user serves as an intermediate layer between the DAO layer and the controller layer.
 * It is ideally used to enforce business rules and ensuring that the controllers only job is to prepare data and send it to the dao, 
 * while the daos layer only responsibility is to query the database and return data.
 * @author Darien Sosa
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor

public class ApplicationService {
	
	private ApplicationRepository appRepo;
	
	public List<Application> getAllApplications(){
		return appRepo.findAll();
	}
	
	public Application getApplicationById(int applicationId) {
		return appRepo.findByApplicationId(applicationId);
	}
	
	public List<Application> getApplicationByApplicantId(User applicantId) {
		return appRepo.findByApplicantId(applicantId);
	}
	
	public List<Application> getApplicationByPostingId(JobPosting postingId) {
		return appRepo.findByPostingId(postingId);
	}
	
	public void insertApplication(Application Application) {
		appRepo.save(Application);
	}
	
	public void updateApplication(Application Application) {
		
		appRepo.save(Application);
	}
	
	public void deleteApplication(Application Application) {
		appRepo.delete(Application);
	}
	
	

}
