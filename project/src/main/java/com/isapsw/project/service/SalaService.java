package com.isapsw.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.isapsw.project.dto.SalaDTO;
import com.isapsw.project.model.Klinika;
import com.isapsw.project.model.Sala;
import com.isapsw.project.repository.SalaRepository;

@Service
public class SalaService {

	@Autowired
	private SalaRepository salaRepository;
	
	@Autowired
	private KlinikaService klinikaService;

	public Sala findOne(Long broj) {
		return salaRepository.findById(broj).orElseGet(null);
	}

	public List<Sala> findAll() {
		return salaRepository.findAll();
	}

	public Page<Sala> findAll(Pageable page) {
		return salaRepository.findAll(page);
	}

	public Sala save(Sala sala) {
		return salaRepository.save(sala);
	}

	public void remove(Long broj) {
		salaRepository.deleteById(broj);
	}

	/*
	 * Logic for addition of a new tuple to database table Sala
	 */
	public ResponseEntity<SalaDTO> addOrdination(SalaDTO salaDTO, String id) {
		if (salaDTO.getNaziv() == "" || salaDTO.getNaziv() == null || salaDTO.getBroj() == -1) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Sala sala = new Sala();
		sala.setNaziv(salaDTO.getNaziv());
		sala.setBroj(salaDTO.getBroj());
		Long idk = (long) -1;
		try {
			idk = Long.parseLong(id);
		}
		catch(Exception e) {
			idk = (long) -1;
		}
		Klinika klinika = klinikaService.findOne(idk);
		if (klinika == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		sala.setKlinika(klinika);
		sala = save(sala);

		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.CREATED);
	}

	/*
	 * Logic for updating of an existing tuple from database table Sala
	 */
	public ResponseEntity<SalaDTO> updateOrdination(SalaDTO salaDTO) {
		Sala sala = findOne(salaDTO.getId());
		if (sala == null || sala.getOperacije().size() != 0
				|| sala.getPregledi().size() != 0 || sala.getNaziv().equals("")
				|| sala.getNaziv() == null || sala.getBroj() == -1) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		sala.setNaziv(salaDTO.getNaziv());
		sala.setBroj(salaDTO.getBroj());
		sala = save(sala);

		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.OK);
	}

	/*
	 * Logic for removing an existing tuple from database table Sala
	 */
	public ResponseEntity<Void> removeOrdination(Long id, Sala sala) {
		if (sala != null && sala.getOperacije().size() == 0
				&& sala.getPregledi().size() == 0) {
			remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
