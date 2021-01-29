package com.jobportal.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Industry;
/**
 * This interfaces acts as a way to query the database, it's only role is to do queries involving Industries.
 * It uses Spring Data to construct its queries
 * @author darie
 */
public interface IndustryRepository extends JpaRepository<Industry, Integer>{
	
	public Industry findByIndustryId(int industryId);
	public Industry findByindustryName(String industryName);
	
}
