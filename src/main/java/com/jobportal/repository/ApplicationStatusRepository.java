package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.ApplicationStatus;
/**
 * This interface acts as a way to query the database, it's only role is to do queries involving application statuses.
 * It uses Spring Data to construct its queries
 * @author darie
 */
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Integer>{

	public ApplicationStatus findByStatusId(int statusId);
	public ApplicationStatus findByStatus(String status);
}
