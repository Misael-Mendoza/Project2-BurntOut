package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.ApplicationStatus;
import com.jobportal.repository.ApplicationStatusRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class ApplicationStatusService {
	
	private ApplicationStatusRepository statusRepo;
	
	public ApplicationStatus getStatusById(int statusId) {
		ApplicationStatus comp = statusRepo.findBystatusId(statusId);
		if(comp != null) {
			return comp;
		}else {
			return null;
		}
	}
	
	public ApplicationStatus getStatusByStatus(String status) {
		ApplicationStatus comp = statusRepo.findBystatus(status);
		if(comp != null) {
			return comp;
		}else {
			return null;
		}
	}
	

}
