package com.isapsw.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isapsw.project.model.IzvestajOPregledu;
import com.isapsw.project.repository.IzvestajOPregleduRepository;

@Service
public class IzvestajOPregleduService {

	@Autowired
	private IzvestajOPregleduRepository izvestajRepository;
	
	public IzvestajOPregledu findOne(Long id) {
		return izvestajRepository.findById(id).orElseGet(null);
	}
	public IzvestajOPregledu save(IzvestajOPregledu izvestaj) {
		return izvestajRepository.save(izvestaj);
	}
	
}
