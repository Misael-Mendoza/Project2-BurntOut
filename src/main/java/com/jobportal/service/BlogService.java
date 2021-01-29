package com.jobportal.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Blog;
import com.jobportal.repository.BlogRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * This service class of Blog serves as an intermediate layer between the DAO layer and the controller layer.
 * It is ideally used to enforce business rules and ensuring that the controllers only job is to prepare data and send it to the dao, 
 * while the daos layer only responsibility is to query the database and return data.
 * @author Darien Sosa
 */

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class BlogService {
	private BlogRepository blogRepo;
	
	public List<Blog> getAllUsers(){
		return blogRepo.findAll();
	}
	
	public Blog getBlogById(int id) {
		return blogRepo.getOne(id);
	}
	
	public Blog getBlogByOwnerId(int id) {
		return blogRepo.findByOwnerId(id);
	}
	
	public Blog getBlogByTitle(String title) {
		return blogRepo.findByBlogTitle(title);
	}
	
	public List<Blog> getBlogByDate(Date date){
		return blogRepo.findByDate(date);
	}
	
	public void insertBlog(Blog blog) {
		blogRepo.save(blog);
	}
	
	public void updateBlog(Blog blog) {
		
		blogRepo.save(blog);
	}
	
	public void deleteBlog(Blog blog) {
		blogRepo.delete(blog);
	}
}
