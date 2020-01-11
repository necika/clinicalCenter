package com.isapsw.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isapsw.project.dto.PacijentDTO;
import com.isapsw.project.dto.UserDTO;
import com.isapsw.project.security.auth.JwtAuthenticationRequest;
import com.isapsw.project.service.PacijentService;

@RestController
@RequestMapping(value = "/isa/pacijent")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<List<PacijentDTO>> getPacijenti() {
		return pacijentService.getAvailablePatients();
	}

	@GetMapping(value = "/allDisablePatients")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<PacijentDTO>> getPatientsDisabled() {
		return pacijentService.getDisabledPatients();
	}

	@GetMapping(value = "/odbijanje/{emailKomentar}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<PacijentDTO>> odbijanje(@PathVariable String emailKomentar) {
		return pacijentService.odbijanjePacijenta(emailKomentar);
	}

	@PostMapping(value = "/prihvatanje", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<PacijentDTO>> prihvatanjePacijenta(@RequestBody UserDTO userDTO) {
		return pacijentService.prihvatanje(userDTO);
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<PacijentDTO> getPacijent(@PathVariable Long id) {
		return pacijentService.getPatientById(id);
	}

	@PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<PacijentDTO> updatePacijent(@RequestBody PacijentDTO pacijentDTO, @PathVariable String id) {
		return pacijentService.updatePatient(pacijentDTO, id);
	}

	@RequestMapping(value = "/potvrdaPrekoEmailaNaBacku", method = RequestMethod.POST)
	public Void potvrdaMaila(@RequestBody JwtAuthenticationRequest authenticationRequest) {
		pacijentService.potvrdaPrekoEmailaNaBacku(authenticationRequest.getUsername());
		return null;
	}

}
