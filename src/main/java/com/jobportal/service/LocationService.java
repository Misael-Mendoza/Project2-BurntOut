package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Industry;
import com.jobportal.model.Location;
import com.jobportal.repository.LocationRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class LocationService {

	private LocationRepository locRepo;

	public Location getLocationById(int locationId) {
		Location loc = locRepo.findBylocationId(locationId);
		if(loc != null) {
			return loc;
		}else {
			return null;
		}
	}
	
	
	
}
