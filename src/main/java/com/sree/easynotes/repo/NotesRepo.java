package com.sree.easynotes.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sree.easynotes.model.NotesModel;

@Repository
public interface NotesRepo extends JpaRepository<NotesModel, Long>{

	
	

}
