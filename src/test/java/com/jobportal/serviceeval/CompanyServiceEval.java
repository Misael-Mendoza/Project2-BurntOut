package com.jobportal.serviceeval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobportal.model.Company;
import com.jobportal.repository.CompanyRepository;
import com.jobportal.service.CompanyService;

@SpringBootTest
public class CompanyServiceEval {
	
	private Company company;
	
	@Mock
	private CompanyRepository comRepo;
	
	@InjectMocks
	private CompanyService compServ;
	
	@BeforeEach
	public void setUp() throws Exception{
		company = new Company(1,"Revature");
		when(comRepo.findByCompanyId(1)).thenReturn(company);
		when(comRepo.findBycompanyName("Revature")).thenReturn(company);
		when(comRepo.findByCompanyId(2)).thenReturn(null);
		when(comRepo.findBycompanyName("Express")).thenReturn(null);
	}
	
	
	@Test
	public void testFindByIdSucess(){
		assertEquals(compServ.getCompanyById(1),company);
	}
	
	@Test
	public void testFindByIdFailure() {
		assertEquals(compServ.getCompanyById(2),null) ;
	}
	
	@Test 
	public void testFindByNameSuccess() {
		assertEquals(compServ.getCompanyByName("Revature"),company);
	}
	
	@Test
	public void testFindByNameFailure() {
		assertEquals(compServ.getCompanyByName("Express"),null) ;
	}
	
}

	
