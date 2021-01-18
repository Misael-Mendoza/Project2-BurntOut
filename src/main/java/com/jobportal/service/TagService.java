package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.Tag;
import com.jobportal.repository.TagRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
@NoArgsConstructor
public class TagService {

	private TagRepository tagRepo;
	
	public Tag getTagById(int tagId) {
		Tag comp = tagRepo.findBytagId(tagId);
		if(comp != null) {
			return comp;
		}else {
			return null;
		}
	}
	
}
