package com.jobportal.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Blog;
/**
 * This interface acts as a way to query the database, it's only role is to do queries involving Blogs.
 * It uses Spring Data to construct its queries
 * @author darie
 */
public interface BlogRepository extends JpaRepository<Blog, Integer>{
	public List<Blog> findAll();
	public Blog findByOwnerId(int id);
	public Blog findByBlogTitle(String title);
	public List<Blog> findByDate(Date date);
	
	
}
