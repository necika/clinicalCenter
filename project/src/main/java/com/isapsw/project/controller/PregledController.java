package com.isapsw.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isapsw.project.dto.LekarDTO;
import com.isapsw.project.dto.PregledDTO;
import com.isapsw.project.model.Pregled;
import com.isapsw.project.service.PregledService;

@RestController
@RequestMapping(value = "isa/pregled")
public class PregledController {

	@Autowired
	private PregledService pregledService;
	
	/*
	 * Sending request for getting all the tuples from table Pregled
	 */
	@GetMapping(value = "/svi")
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('PATIENT')")
	public ResponseEntity<List<PregledDTO>> getPregledi() {
		return pregledService.getAvailableAppointments();
	}
	
	/*
	 * Sending request for getting paginated tuples from table Pregled
	 */
	@GetMapping(value = "/")
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('PATIENT')")
	public ResponseEntity<List<PregledDTO>> getLekariPage(Pageable page) {
		Page<Pregled> pregledi = pregledService.findAll(page);
		List<PregledDTO> preglediDTO = new ArrayList<>();
		for (Pregled p : pregledi) {
			preglediDTO.add(new PregledDTO(p));
		}

		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}
	
	/*
	 * Sending request to database table Pregled for tuple specified by given ID
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN') or hasRole('NURSE')")
	public ResponseEntity<PregledDTO> getPregled(@PathVariable Long id) {
		return pregledService.getAppointmentById(id);
	}
	
	/*
	 * Receiving data from front-end part of application and injecting it as a
	 * new tuple in table Pregled
	 */
	@PostMapping(value = "/add/{entiteti}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PregledDTO> saveLekar(@PathVariable String entiteti, @RequestBody PregledDTO pregledDTO) {
		ResponseEntity<PregledDTO> pregled = pregledService.addPregled(entiteti, pregledDTO);
		return pregled;
	}
	
	@PostMapping(value = "/addDodatniPregled/{entiteti}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PregledDTO> saveNoviPregled(@PathVariable String entiteti, @RequestBody PregledDTO pregledDTO) {
		return pregledService.addDodatniPregled(pregledDTO, entiteti);
	}

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<PregledDTO>> getSlobodniPregledi() {
		List<PregledDTO> preglediDTO = pregledService.findAllByRezervisan(false);
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}
	@GetMapping(value = "/zakaziPredefinisaniPregled/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<PregledDTO>> zakaziPredefinisaniPregled(@PathVariable Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<PregledDTO> preglediDTO = pregledService.zakaziPredefinisaniPregledService(authentication.getName(),id);
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}
	
}
