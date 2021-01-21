package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{

	public Company findByCompanyId(int companyId);
	public Company findByCompanyName(String companyName);
	
}
