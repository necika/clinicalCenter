package com.isapsw.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isapsw.project.service.LekarService;

@RestController
@RequestMapping(value = "isa/odmor")
public class ZahtevZaOdmorController {
	
	@Autowired
	private LekarService lekarService;
	
	/*
	 * Vacation/time-off request sending to administrator of clinic
	 */
	/*@PostMapping(value = "/lekar/{datumi}", consumes = "application/json")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<LekarDTO> kreiranjeZahtevaZaOdmor(@PathVariable String datumi) {
		return lekarService.slanjeZahtevaZaOdmor(datumi);
	}*/
	
}
