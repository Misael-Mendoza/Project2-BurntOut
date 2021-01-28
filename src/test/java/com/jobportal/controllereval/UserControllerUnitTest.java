package com.jobportal.controllereval;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.controller.UserController;
import com.jobportal.model.Company;
import com.jobportal.model.User;
import com.jobportal.model.UserRole;
import com.jobportal.service.CompanyService;
import com.jobportal.service.UserRoleService;
import com.jobportal.service.UserService;

@SpringBootTest
public class UserControllerUnitTest {
	
	@MockBean
	private UserService uServ;
	@MockBean
	private UserRoleService urServ;
	@MockBean
	private CompanyService cServ;
	
	
	@InjectMocks
	private UserController uCon;
	
	private User user;
	private MockMvc mvc;
	
	@Mock
	private UserRole uRole;
	@Mock
	private Company comp;
	
	@BeforeEach
	public void setUp() throws Exception {

		user = new User("Ryan", "Curley", "ryancurley72@gmail.com", "ryan", "5430E0ECA9C929C5A47EFFC526E88D65522E44F66A45F021F8DA28EA76462F6D", 
				"12F13107450F12CBF7F0BE83813B2658", uRole, comp);
		uCon = new UserController(uServ, urServ, cServ);
		mvc = MockMvcBuilders.standaloneSetup(uCon).build();
		
		doNothing().when(uServ).insertUser(user);
		doNothing().when(uServ).encryptPassword(anyString(), anyString());
		doNothing().when(uServ).deleteUser(user);
		doNothing().when(uServ).updateUser(user);
		doNothing().when(uServ).sendRecoveryEmail(anyString(), anyString());

		when(uServ.getUserByUserId(11)).thenReturn(user);
		when(uServ.getUserByUsername("ryan")).thenReturn(user);
		when(uServ.getUserByEmail(anyString())).thenReturn(user);
		when(urServ.getRoleByName("Candidate")).thenReturn(uRole);
		when(cServ.getCompanyByName("GlobalCom")).thenReturn(comp);
		when(uServ.verifyUser("ryan", "5430E0ECA9C929C5A47EFFC526E88D65522E44F66A45F021F8DA28EA76462F6D")).thenReturn(true);
		when(uServ.getUserBySecurityCode(anyString())).thenReturn(user);
	}
	
//	@Test
//	public void postUserTest() throws Exception {
//		mvc.perform(post("/users/newuser").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user)))
//		.andExpect(status().isCreated()).andExpect(jsonPath("$").value("User Successfully Created!"));
//	}

	@Test
	public void postLoginTest() throws Exception {
		mvc.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.userId").value(user.getUserId()));
	}
	
	@Test
	public void postRecoveryEmail() throws Exception {
		mvc.perform(post("/users/recover").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void postPasswordReset() throws Exception {
		mvc.perform(post("/users/passwordreset").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user)))
		.andExpect(status().isAccepted());
	}

	@Test
	public void getUserByUsernameTest() throws Exception {
		mvc.perform(get("/users/username/{username}", user.getUsername())).andExpect(status().isOk())
		.andExpect(jsonPath("$.username", is(user.getUsername())));
	}

	@Test
	public void getUserByEmailTest() throws Exception {
		mvc.perform(get("/users/email/{email}", user.getEmail())).andExpect(status().isOk())
		.andExpect(jsonPath("$.email", is(user.getEmail())));
	}

}
