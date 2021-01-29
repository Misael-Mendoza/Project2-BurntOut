package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Industry;
import com.jobportal.model.JobPosting;
import com.jobportal.repository.IndustryRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * This service class of Industry serves as an intermediate layer between the DAO layer and the controller layer.
 * It is ideally used to enforce business rules and ensuring that the controllers only job is to prepare data and send it to the dao, 
 * while the daos layer only responsibility is to query the database and return data.
 * @author Darien Sosa
 */
@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class IndustryService {

	private IndustryRepository indRepo;
	
	public Industry getIndustryById(int industryId) {
		Industry industry = indRepo.findByIndustryId(industryId);
		if(industry != null) {
			return industry;
		}else {
			return null;
		}
	}
	
	public Industry getIndustryByName(String industryName) {
		Industry industry = indRepo.findByindustryName(industryName);
		if(industry != null) {
			return industry;
		}else {
			return null;
		}
	}
	
	public void insertIndustry(Industry ind) {
		indRepo.save(ind);
	}
	
	
}
