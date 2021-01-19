package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Location;



public interface LocationRepository extends JpaRepository<Location, Integer> {
	
	public Location findByLocationId(int locationId);
	public Location findByLocationName(String locationName);
}
