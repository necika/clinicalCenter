package com.isapsw.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.isapsw.project.dto.SifarnikDTO;
import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.SifarnikDijagnozaILekova;
import com.isapsw.project.repository.SifarnikRepository;

@Service
public class SifarnikDijagnozaILekovaService {

	@Autowired
	private SifarnikRepository sifarnikRepository;

	public SifarnikDijagnozaILekova findOne(Long id) {
		return sifarnikRepository.findById(id).orElseGet(null);
	}
	
	public SifarnikDijagnozaILekova findByLekovi(String lekovi) {
		return sifarnikRepository.findByLekovi(lekovi);
	}

	public List<SifarnikDijagnozaILekova> findAll() {
		return sifarnikRepository.findAll();
	}

	public Page<SifarnikDijagnozaILekova> findAll(Pageable page) {
		return sifarnikRepository.findAll(page);
	}

	public SifarnikDijagnozaILekova save(SifarnikDijagnozaILekova sifarnik) {
		return sifarnikRepository.save(sifarnik);
	}

	public void remove(Long id) {
		sifarnikRepository.deleteById(id);
	}
	
	/*
	 * Getting all available tuples from database table SifarnikDijagnozaILekova
	 */
	public ResponseEntity<List<SifarnikDTO>> getAvailableCombinations() {
		List<SifarnikDijagnozaILekova> sifarnici = findAll();
		List<SifarnikDTO> sifarniciDTO = new ArrayList<>();
		for (SifarnikDijagnozaILekova s : sifarnici) {
			sifarniciDTO.add(new SifarnikDTO(s));
		}

		return new ResponseEntity<>(sifarniciDTO, HttpStatus.OK);
	}
	
	/*
	 * Logic for addition of a new tuple to database table Sifarnik
	 */
	public ResponseEntity<SifarnikDTO> addCombination(SifarnikDTO sifarnikDTO) {
		if (sifarnikDTO.getDijagnoze() == null || sifarnikDTO.getLekovi() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		SifarnikDijagnozaILekova sifarnikSave = new SifarnikDijagnozaILekova();
		sifarnikSave.setDijagnoze(sifarnikDTO.getDijagnoze());
		sifarnikSave.setId(sifarnikDTO.getId());
		sifarnikSave.setLekovi(sifarnikDTO.getLekovi());
		sifarnikSave = save(sifarnikSave);
		
		return new ResponseEntity<>(new SifarnikDTO(sifarnikSave), HttpStatus.CREATED);
	}

}
