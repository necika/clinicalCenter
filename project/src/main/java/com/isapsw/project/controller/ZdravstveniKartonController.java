package com.isapsw.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isapsw.project.dto.ZdravstveniKartonDTO;
import com.isapsw.project.service.ZdravstveniKartonService;

@RestController
@RequestMapping(value = "isa/zdravstveniKarton")
public class ZdravstveniKartonController {

	@Autowired
	private ZdravstveniKartonService kartonService;
	
	@GetMapping(value = "/zdravstveniKartonPacijenta/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<ZdravstveniKartonDTO> getZdravstveniKartonPacijenta(@PathVariable Long id) {
		return kartonService.getZdravstveniKartonPacijentaService(id);
	}
	
	@GetMapping(value = "/addIzvestajOPregledu/{parametri}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DOCTOR')")
	public Void addIzvestaj(@PathVariable String parametri) {
		kartonService.addIzvestaj(parametri);
		return null;
	}
	
	@PutMapping(value = "/izmeni/{osnovniPodaciID}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<ZdravstveniKartonDTO> izmenaZdravstvenogKartona(@PathVariable String osnovniPodaciID) {
		return kartonService.izmenaZdravstvenogKartona(osnovniPodaciID);
	}
	
}
