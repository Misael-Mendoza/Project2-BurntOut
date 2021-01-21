package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Company;
import com.jobportal.repository.CompanyRepository;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class CompanyService {

	private CompanyRepository comRepo;
	
	public Company getCompanyById(int companyId) {
		Company comp = comRepo.findByCompanyId(companyId);
		if(comp != null) {
			return comp;
		}else {
			return null;
		}
	}
	
	public Company getCompanyByName(String companyName) {
		Company comp = comRepo.findByCompanyName(companyName);
		if(comp != null) {
			return comp;
		}else {
			return null;
		}
	}
	
}
