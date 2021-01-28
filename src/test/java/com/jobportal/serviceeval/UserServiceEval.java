package com.jobportal.serviceeval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobportal.model.Company;
import com.jobportal.model.User;
import com.jobportal.model.UserRole;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.UserService;

@SpringBootTest
public class UserServiceEval {
	
	@Mock
	private UserRepository uRepo;

	@Mock
	private UserRole uRole;	
	
	@Mock
	private Company comp;
	
	private User user;
	private List<User> uList = new ArrayList<>();
	
	@InjectMocks
	private UserService uServ;
	
	@BeforeEach
	public void setUp() throws Exception {
		
		user = new User("Ryan", "Curley", "ryancurley72@gmail.com", "ryan", "5430E0ECA9C929C5A47EFFC526E88D65522E44F66A45F021F8DA28EA76462F6D", 
				"12F13107450F12CBF7F0BE83813B2658", uRole, comp);
		uList.add(user);
		when(uRepo.findByUserId(11)).thenReturn(user);
		when(uRepo.findByUserId(0)).thenReturn(null);
		when(uRepo.findByEmail("ryancurley72@gmail.com")).thenReturn(user);
		when(uRepo.findByEmail("fakeemail")).thenReturn(null);
		when(uRepo.findByUsername("ryan")).thenReturn(user);
		when(uRepo.findByUsername("notryan")).thenReturn(null);
		when(uRepo.findAll()).thenReturn(uList);

	}
	
	@Test
	public void testFindByIdSuccess() {
		assertEquals(uServ.getUserByUserId(11), user);
	}
	
	@Test
	public void testFindByIdFailure() {
		assertEquals(uServ.getUserByUserId(0),null);
	}
	
	@Test
	public void testFindByEmailSuccess() {
		assertEquals(uServ.getUserByEmail("ryancurley72@gmail.com"), user);
	}
	
	@Test
	public void testFindByEmailFailure() {
		assertEquals(uServ.getUserByEmail("fakeemail"),null);
	}
	
	@Test
	public void testFindByUsernameSuccess() {
		assertEquals(uServ.getUserByUsername("ryan"), user);
	}
	
	@Test
	public void testFindByUsernameFailure() {
		assertEquals(uServ.getUserByUsername("notryan"),null);
	}
	
	@Test
	public void testFindAll() {
		assertEquals(uServ.getAllUsers(), uList);
	}

}
