package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Tag;
import com.jobportal.repository.TagRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * This service class of tag serves as an intermediate layer between the DAO layer and the controller layer.
 * It is ideally used to enforce business rules and ensuring that the controllers only job is to prepare data and send it to the dao, 
 * while the daos layer only responsibility is to query the database and return data.
 * @author Darien Sosa
 */
@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class TagService {

	private TagRepository tagRepo;
	
	public Tag getTagById(int tagId) {
		Tag comp = tagRepo.findByTagId(tagId);
		if(comp != null) {
			return comp;
		}else {
			return null;
		}
	}
	
}
