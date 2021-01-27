package com.jobportal.serviceeval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobportal.model.Industry;
import com.jobportal.repository.IndustryRepository;
import com.jobportal.service.IndustryService;

@SpringBootTest
public class IndustryServiceEval {

	private Industry industry;
	
	@Mock
	private IndustryRepository indRepo;
	
	
	@InjectMocks
	private IndustryService indServ;
	
	@BeforeEach
	public void setUp() throws Exception{
		industry = new Industry(1,"IT");
		when(indRepo.findByIndustryId(1)).thenReturn(industry);
		when(indRepo.findByindustryName("IT")).thenReturn(industry);
		when(indRepo.findByIndustryId(2)).thenReturn(null);
		when(indRepo.findByindustryName("Detroit")).thenReturn(null);
	}
	
	@Test
	public void testFindByIdSucess(){
		assertEquals(indServ.getIndustryById(1),industry);
	}
	
	@Test
	public void testFindByIdFailure() {
		assertEquals(indServ.getIndustryById(2),null) ;
	}
	
	@Test 
	public void testFindByNameSuccess() {
		assertEquals(indServ.getIndustryByName("IT"),industry);
	}
	
	@Test
	public void testFindByNameFailure() {
		assertEquals(indServ.getIndustryByName("CS"),null) ;
	}
}
