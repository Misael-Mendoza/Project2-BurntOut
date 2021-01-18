package com.jobportal.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Industry;

public interface IndustryRepository extends JpaRepository<Industry, Integer>{
	
	public Industry findByindustryId(int industryId);
	
}
