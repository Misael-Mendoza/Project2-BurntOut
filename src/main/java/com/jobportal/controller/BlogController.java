package com.jobportal.controller;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.model.Blog;
import com.jobportal.model.User;
import com.jobportal.service.BlogService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value = "/blogs")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class BlogController {
	private BlogService blogServ;
	
	@CrossOrigin(origins = "*")
	@PostMapping(value="/add")
	public ResponseEntity<String> insertBlog(@RequestBody LinkedHashMap bMap){
		Date blogDate = new Date(System.currentTimeMillis());
		Blog blog = new Blog(
				new User(1),
				(String)bMap.get("title"),
				blogDate,
				(String)bMap.get("message"));
		blogServ.insertBlog(blog);
		return new ResponseEntity<>("Blog Successfully Created!",HttpStatus.CREATED); 
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/all")
	public ResponseEntity<List<Blog>> getAllBlog(){
		List<Blog> blogList = blogServ.getAllUsers();
		return blogList.size() == 0 ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<>(blogList, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Blog> getBlogById(@PathVariable("id") int id){
		Blog blog = blogServ.getBlogById(id);
		return blog != null ? new ResponseEntity<>(blog,HttpStatus.OK) :new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<Blog> getBlogByTitle(@PathVariable("title") String title){
		Blog blog = blogServ.getBlogByTitle(title);
		return blog != null ? new ResponseEntity<>(blog,HttpStatus.OK) : new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
//	@GetMapping("/date/{date}")
//	public ResponseEntity<Blog>getBlogByDate(@PathVariable("date") String date){
//		
//	}
//	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBlog(@PathVariable("id") int id){
		Blog blog = blogServ.getBlogById(id);
		if (blog != null) {
			blogServ.deleteBlog(blog);
			return new ResponseEntity<>("Blog Deleted!",HttpStatus.GONE);
		} 
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
}
