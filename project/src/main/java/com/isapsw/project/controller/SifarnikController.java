package com.isapsw.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isapsw.project.dto.SifarnikDTO;
import com.isapsw.project.service.SifarnikDijagnozaILekovaService;

@RestController
@RequestMapping(value = "isa/sifarnici")
public class SifarnikController {

	@Autowired
	private SifarnikDijagnozaILekovaService sifarnikService;
	
	/*
	 * Sending request for getting all the tuples in table SifarnikDijagnozaILekova
	 */
	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
	public ResponseEntity<List<SifarnikDTO>> getSifarnici() {
		return sifarnikService.getAvailableCombinations();
	}
	
	@PostMapping(value = "/dodavanje", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SifarnikDTO> kreiranjeSifarnika(@RequestBody SifarnikDTO sifarnikDTO) {
		return sifarnikService.addCombination(sifarnikDTO);
	}
	
}
