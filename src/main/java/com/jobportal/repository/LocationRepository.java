package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Location;


/**
 * This interface acts as a way to query the database, it's only role is to do queries involving locations.
 * It uses Spring Data to construct its queries
 * @author darie
 */
public interface LocationRepository extends JpaRepository<Location, Integer> {
	
	public Location findByLocationId(int locationId);
	public Location findByLocationName(String locationName);
}
