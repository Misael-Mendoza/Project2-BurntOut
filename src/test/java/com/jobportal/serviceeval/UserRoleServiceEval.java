package com.jobportal.serviceeval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobportal.model.User;
import com.jobportal.model.UserRole;
import com.jobportal.repository.UserRoleRepository;
import com.jobportal.service.UserRoleService;

@SpringBootTest
public class UserRoleServiceEval {
	
	@Mock
	private UserRoleRepository urRepo;
	
	@Mock
	private List<User> uList;

	private UserRole uRole;

	@InjectMocks
	private UserRoleService urServ;
	
	@BeforeEach
	public void setUp() throws Exception {
		uRole = new UserRole(1, "Candidate", uList);
		when(urRepo.findByUserRoleID(1)).thenReturn(uRole);
		when(urRepo.findByUserRoleID(0)).thenReturn(null);
		when(urRepo.findByUserRole("Candidate")).thenReturn(uRole);
		when(urRepo.findByUserRole("none")).thenReturn(null);
	}
	
	@Test
	public void testFindByIdSuccess() {
		assertEquals(urServ.getRoleById(1),uRole);
	}
	
	@Test
	public void testFindByIdFailure() {
		assertEquals(urServ.getRoleById(0), null);
	}
	
	@Test
	public void testFindByNameSuccess() {
		assertEquals(urServ.getRoleByName("Candidate"),uRole);
	}
	
	@Test
	public void testFindByNameFailure() {
		assertEquals(urServ.getRoleByName("none"), null);
	}
	
}
