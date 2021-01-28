package com.jobportal.serviceeval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobportal.model.Location;
import com.jobportal.repository.LocationRepository;
import com.jobportal.service.LocationService;

@SpringBootTest
public class LocationServiceEval {

	private Location location;
	
	@Mock
	private LocationRepository locRepo;
	
	
	@InjectMocks
	private LocationService locServ;
	
	@BeforeEach
	public void setUp() throws Exception{
		location = new Location(1,"London");
		when(locRepo.findByLocationId(1)).thenReturn(location);
		when(locRepo.findByLocationName("London")).thenReturn(location);
		when(locRepo.findByLocationId(2)).thenReturn(null);
		when(locRepo.findByLocationName("Detroit")).thenReturn(null);
	}
	
	@Test
	public void testFindByIdSucess(){
		assertEquals(locServ.getLocationById(1),location);
	}
	
	@Test
	public void testFindByIdFailure() {
		assertEquals(locServ.getLocationById(2),null) ;
	}
	
	@Test 
	public void testFindByNameSuccess() {
		assertEquals(locServ.getLocationByName("London"),location);
	}
	
	@Test
	public void testFindByNameFailure() {
		assertEquals(locServ.getLocationByName("Detroit"),null) ;
	}
	
}
