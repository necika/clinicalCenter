package com.isapsw.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isapsw.project.dto.MedicinskaSestraDTO;
import com.isapsw.project.dto.ReceptDTO;
import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.service.MedicinskaSestraService;


@RestController
@RequestMapping(value = "isa/medicinskaSestra")
public class MedicinskaSestraController {

	@Autowired
	private MedicinskaSestraService sestraService;
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('NURSE')")
	public ResponseEntity<List<MedicinskaSestraDTO>> getSestre() {
		return sestraService.getAvailableNurses();
	}
	
	@GetMapping(value = "/receptiZaOveru")
	@PreAuthorize("hasRole('NURSE')")
	public ResponseEntity<List<ReceptDTO>> receptiZaOveru() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return sestraService.getReceptiZaOveruService(authentication);
	}
	
	@DeleteMapping(value = "/overaRecepta/{id}")
	@PreAuthorize("hasRole('NURSE')")
	public ResponseEntity<Void> overaReceptaa(@PathVariable Long id) {
		return sestraService.overaReceptaService(id);
	}
	
	/*
	 * Sending request to database table MedicinskaSestra for tuple specified by given ID
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('NURSE')")
	public ResponseEntity<MedicinskaSestraDTO> getSestra(
			@PathVariable Long id) {
		return sestraService.getNurseById(id);
	}
	
	/*
	 * Receiving data from front-end part of application and injecting it as a
	 * modified tuple in table MedicinskaSestra
	 */
	@PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('NURSE')")
    public ResponseEntity<MedicinskaSestraDTO> updateSestra(@RequestBody MedicinskaSestraDTO sestraDTO, @PathVariable String id) {
        if (!sestraService.checkSestraConstraints(sestraDTO)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MedicinskaSestra sestra = sestraService.updateSestra(sestraDTO);
        		
        return new ResponseEntity<>(new MedicinskaSestraDTO(sestra), HttpStatus.OK);
    }

	@PutMapping(value = "/kalendar/{datumi}")
	@PreAuthorize("hasRole('NURSE')")
	public ResponseEntity<MedicinskaSestraDTO> kreiranjeZahtevaZaOdmor(@PathVariable String datumi) {
		return sestraService.slanjeZahtevaZaOdmor(datumi);
	}
  
	@GetMapping(value = "/radnoVreme")
	@PreAuthorize("hasRole('NURSE')")
	public ResponseEntity<String> getRadnoVreme() {
		return sestraService.getRadnoVreme();
	}
	
	@PostMapping(value = "/add/{klinika}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MedicinskaSestraDTO> saveLekar(@PathVariable String klinika, @RequestBody MedicinskaSestraDTO sestraDTO) {
		return sestraService.addNurse(klinika, sestraDTO);
	}
	@DeleteMapping(value = "/remove/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteMedicinskaSestra(@PathVariable Long id) {
		MedicinskaSestra sestra = sestraService.findOne(id);
		return sestraService.removeNurse(id, sestra);
	}

}
