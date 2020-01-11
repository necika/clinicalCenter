package com.isapsw.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isapsw.project.dto.SalaDTO;
import com.isapsw.project.model.Sala;
import com.isapsw.project.service.SalaService;

@RestController
@RequestMapping(value = "isa/sale")
public class SalaController {

	@Autowired
	private SalaService salaService;

	/*
	 * Sending request for getting all the tuples in table Sala and feeding it
	 * to the front-end part of application
	 */
	@GetMapping
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<List<SalaDTO>> getSale() {
		List<Sala> sale = salaService.findAll();
		List<SalaDTO> saleDTO = new ArrayList<>();
		for (Sala s : sale) {
			saleDTO.add(new SalaDTO(s));
		}

		return new ResponseEntity<>(saleDTO, HttpStatus.OK);
	}

	/*
	 * Receiving data from front-end part of application and injecting it as a
	 * new tuple in table Sala
	 */
	@PostMapping(value = "/add/{idKlinike}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SalaDTO> saveSala(@PathVariable String idKlinike, @RequestBody SalaDTO salaDTO) {
		return salaService.addOrdination(salaDTO, idKlinike);
	}

	/*
	 * Sending request to database table Sala for tuple specified by given ID
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<SalaDTO> getSala(@PathVariable Long id) {
		Sala sala = salaService.findOne(id);
		if (sala == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.OK);
	}

	/*
	 * Receiving data from front-end part of application and updating the
	 * existing tuple in table Sala
	 */
	@PutMapping(value = "/update/{id}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SalaDTO> updateSala(@RequestBody SalaDTO salaDTO) {
		return salaService.updateOrdination(salaDTO);
	}

	/*
	 * Removing tuple selected by user on front-end part of application from
	 * database
	 */
	@DeleteMapping(value = "/remove/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteSala(@PathVariable Long id) {
		Sala sala = salaService.findOne(id);
		return salaService.removeOrdination(id, sala);
	}

}
