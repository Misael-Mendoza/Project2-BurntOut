package com.jobportal.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer>{
	public List<Blog> findAll();
	public Blog findByOwnerId(int id);
	public Blog findByBlogTitle(String title);
	public List<Blog> findByDate(Date date);
	
	
}
