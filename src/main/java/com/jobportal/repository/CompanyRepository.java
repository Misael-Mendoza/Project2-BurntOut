package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Company;
/**
 * This interface acts as a way to query the database, it's only role is to do queries involving companies.
 * It uses Spring Data to construct its queries
 * @author darie
 */
public interface CompanyRepository extends JpaRepository<Company, Integer>{

	public Company findByCompanyId(int companyId);
	public Company findBycompanyName(String companyName);
	
}
