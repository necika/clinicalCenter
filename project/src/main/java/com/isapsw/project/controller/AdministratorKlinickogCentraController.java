package com.isapsw.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isapsw.project.dto.AdministratorKlinickogCentraDTO;
import com.isapsw.project.service.AdministratorKlinickogCentraService;

@RestController
@RequestMapping(value = "isa/adminKlinickogCentra")
public class AdministratorKlinickogCentraController {

	@Autowired
	private AdministratorKlinickogCentraService adminKlinickogCentraService;

	@PostMapping(value = "/dodavanjeAdministratoraKlinickogCentra", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdministratorKlinickogCentraDTO> dodavanjeNovogAdministratoraKlinickogCentra(
			@RequestBody AdministratorKlinickogCentraDTO adminDTO) {
		return adminKlinickogCentraService.addAdmin(adminDTO);
	}

	@PutMapping(value = "/promenaLozinke/{lozinka}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdministratorKlinickogCentraDTO> promenaLozinke(@PathVariable String lozinka) {
		return adminKlinickogCentraService.changePassword(lozinka);
	}

}
