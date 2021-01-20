package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Industry;
import com.jobportal.repository.IndustryRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
	
	
}
