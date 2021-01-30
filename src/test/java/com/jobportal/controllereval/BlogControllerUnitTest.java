package com.jobportal.controllereval;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.controller.BlogController;
import com.jobportal.model.Blog;
import com.jobportal.model.User;
import com.jobportal.service.BlogService;

@SpringBootTest
public class BlogControllerUnitTest {
	
	@Mock
	private BlogService bServ;
	
	@InjectMocks
	private BlogController bCon;
	
	private Blog blog;
	private Blog blog2;
	private List<Blog> blogs;
	private MockMvc mock;
	
	@BeforeEach
	public void setUp() throws Exception{
		User user = new User(9);
		Date date = new Date(System.currentTimeMillis());
		List<Blog> blogs = new ArrayList<>();
		blog = new Blog(user,"Hello",date,"Hello World!");
		blog2 = new Blog(user,"Hi",date,"hi again");
		blogs.add(blog);
		blogs.add(blog2);
		mock = MockMvcBuilders.standaloneSetup(bCon).build();
		doNothing().when(bServ).insertBlog(blog);
		doNothing().when(bServ).deleteBlog(blog);
		when(bServ.getBlogByOwnerId(9)).thenReturn(blog);
		when(bServ.getBlogByTitle("Hello")).thenReturn(blog);
	}
	
	/* Tests that the controller to get add a blog posts behaves as expected when hitting the corresponding endpoint. Also tests to make sure that we receive the
	 * correct data and the correct status code from the server.	
	*/
	@Test
	public void postBlogTest() throws Exception{
		mock.perform(post("/blogs/add").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(blog)))
		.andExpect(status().isCreated()).andExpect(jsonPath("$").value("Blog Successfully Created!"));
	}
	
	/* Tests that the controller to get delete a blog post behaves as expected when hitting the corresponding endpoint. Also tests to make sure that we receive the
	 * correct data and the correct status code from the server.	
	*/
	@Test 
	public void deleteBlogTest() throws Exception{
		mock.perform(delete("/blogs/{id}",blog.getBlogId())).andExpect(status().isGone()).andExpect(jsonPath("$").value("Blog Deleted!"));
	}
	
	/* Tests that the controller to get all blog posts behaves as expected when hitting the corresponding endpoint. Also tests to make sure that we receive the
	 * correct data and the correct status code from the server.	
	*/
	@Test
	public void getBlogTest() throws Exception{
		mock.perform(get("/blogs/title/{title}",blog.getBlogTitle())).andExpect(status().isOk()).andExpect(jsonPath("$.blogTitle",is(blog.getBlogTitle())))
		.andExpect(jsonPath("$.blogContent", is(blog.getBlogContent())));
	}
	
}
