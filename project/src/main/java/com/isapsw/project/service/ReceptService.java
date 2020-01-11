package com.isapsw.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Recept;
import com.isapsw.project.repository.ReceptRepository;

@Service
public class ReceptService {

	@Autowired
	private ReceptRepository receptRepository;
	
	public Recept findOne(Long id) {
		return receptRepository.findById(id).orElseGet(null);
	}

	public List<Recept> findAll() {
		return receptRepository.findAll();
	}
	
	public Recept save(Recept recept) {
		return receptRepository.save(recept);
	}
	
}
