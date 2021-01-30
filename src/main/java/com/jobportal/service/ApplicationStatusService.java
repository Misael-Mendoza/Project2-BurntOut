package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.ApplicationStatus;
import com.jobportal.repository.ApplicationStatusRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * This service class of ApplicationStatus serves as an intermediate layer between the DAO layer and the controller layer.
 * It is ideally used to enforce business rules and ensuring that the controllers only job is to prepare data and send it to the dao, 
 * while the daos layer only responsibility is to query the database and return data.
 * @author Darien Sosa
 */
@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class ApplicationStatusService {
	
	private ApplicationStatusRepository statusRepo;
	
	public ApplicationStatus getStatusById(int statusId) {
		ApplicationStatus comp = statusRepo.findByStatusId(statusId);
		if(comp != null) {
			return comp;
		}else {
			return null;
		}
	}
	
	public ApplicationStatus getStatusByStatus(String status) {
		ApplicationStatus comp = statusRepo.findByStatus(status);
		if(comp != null) {
			return comp;
		}else {
			return null;
		}
	}
	

}
