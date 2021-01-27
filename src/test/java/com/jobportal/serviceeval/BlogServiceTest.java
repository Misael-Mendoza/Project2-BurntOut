package com.jobportal.serviceeval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobportal.model.Blog;
import com.jobportal.model.User;
import com.jobportal.repository.BlogRepository;
import com.jobportal.service.BlogService;

@SpringBootTest
public class BlogServiceTest {

	@Mock
	private BlogRepository bDao;
	
	@InjectMocks
	private BlogService bServ;
	
	private Blog blog;
	private List<Blog> blogList = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		User user = new User(9);
		Date date = new Date(System.currentTimeMillis());
		blog = new Blog(user,"Hello",date,"Hello World!");
		when(bDao.findByOwnerId(9)).thenReturn(blog);
		when(bDao.findByOwnerId(100)).thenReturn(null);
		when(bDao.findByBlogTitle("Hello")).thenReturn(blog);
		when(bDao.findByBlogTitle("adsasdsads")).thenReturn(null);
		when(bDao.findAll()).thenReturn(blogList);
		
	}
	
	@Test
	public void testFindAllSuccess() {
		assertEquals(bServ.getAllUsers(), blogList);
	}
	
	
	@Test
	public void testFindByOwnerIdSuccess() {
		assertEquals(bServ.getBlogByOwnerId(9).getBlogTitle(), blog.getBlogTitle());
	}
	
	@Test
	public void testFindByOwnerIdFailure() {
		assertEquals(bServ.getBlogByOwnerId(100), null);
	}
	
	@Test
	public void testFindByTitleSuccess() {
		assertEquals(bServ.getBlogByTitle("Hello").getBlogTitle(), blog.getBlogTitle());
	}
	
	@Test
	public void testFindByTitleFailure() {
		assertEquals(bServ.getBlogByTitle("adsasdsads"),null);
	}
}
