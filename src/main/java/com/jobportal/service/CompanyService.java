package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Company;
import com.jobportal.model.Industry;
import com.jobportal.repository.CompanyRepository;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * This service class of Company serves as an intermediate layer between the DAO layer and the controller layer.
 * It is ideally used to enforce business rules and ensuring that the controllers only job is to prepare data and send it to the dao, 
 * while the daos layer only responsibility is to query the database and return data.
 * @author Darien Sosa
 */
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
		Company comp = comRepo.findBycompanyName(companyName);
		if(comp != null) {
			return comp;
		}else {
			return null;
		}
	}
	
	public void insertCompany(Company com) {
		comRepo.save(com);
	}
	
}
