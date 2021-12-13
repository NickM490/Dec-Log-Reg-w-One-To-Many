package com.codingdojo.loginandreg.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.loginandreg.models.Idea;

@Repository
public interface ideaRepository extends CrudRepository<Idea, Long>{
	
	List<Idea> findAll();
	
}
