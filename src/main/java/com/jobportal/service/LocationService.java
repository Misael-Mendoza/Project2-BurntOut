package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Industry;
import com.jobportal.model.Location;
import com.jobportal.repository.LocationRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * This service class of Location serves as an intermediate layer between the DAO layer and the controller layer.
 * It is ideally used to enforce business rules and ensuring that the controllers only job is to prepare data and send it to the dao, 
 * while the daos layer only responsibility is to query the database and return data.
 * @author Darien Sosa
 */
@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class LocationService {

	private LocationRepository locRepo;

	public Location getLocationById(int locationId) {
		Location loc = locRepo.findByLocationId(locationId);
		if(loc != null) {
			return loc;
		}else {
			return null;
		}
	}
	
	public Location getLocationByName(String locationName) {
		Location loc = locRepo.findByLocationName(locationName);
		if(loc != null) {
			return loc;
		}else {
			return null;
		}
	}
	
	public void insertLocation(Location loc) {
		locRepo.save(loc);
	}
}
