package com.isapsw.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.isapsw.project.dto.LekarDTO;
import com.isapsw.project.dto.PregledDTO;
import com.isapsw.project.dto.ZahtevZaTerminDTO;
import com.isapsw.project.model.Lekar;
import com.isapsw.project.service.LekarService;

@RestController
@RequestMapping(value = "isa/lekar")
public class LekarController {

	@Autowired
	private LekarService lekarService;

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<List<LekarDTO>> getLekari() {
		return lekarService.getAvailableDoctors();
	}

	@GetMapping(value = "/")
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<List<LekarDTO>> getLekariPage(Pageable page) {
		Page<Lekar> lekari = lekarService.findAll(page);
		List<LekarDTO> lekariDTO = new ArrayList<>();
		for (Lekar l : lekari) {
			lekariDTO.add(new LekarDTO(l));
		}

		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}

	/*
	 * Sending request to database table Lekar for tuple specified by given ID
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN') or hasRole('PATIENT')")
	public ResponseEntity<LekarDTO> getLekar(@PathVariable Long id) {
		return lekarService.getDoctorById(id);
	}

	/*
	 * Receiving data from front-end part of application and injecting it as a
	 * new tuple in table Lekar
	 */
	@PostMapping(value = "/add/{klinika}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<LekarDTO> saveLekar(@PathVariable String klinika, @RequestBody LekarDTO lekarDTO) {
		return lekarService.addDoctor(klinika, lekarDTO);
	}
	
	/*
	 * Receiving data from front-end part of application and injecting it as a
	 * modified tuple in table Lekar
	 */
	@PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<LekarDTO> updateLekar(@RequestBody LekarDTO lekarDTO, @PathVariable String id) {
        return lekarService.updateDoctor(lekarDTO);
    }

	/*
	 * Removing tuple selected by user on front-end part of application from
	 * database
	 */
	@DeleteMapping(value = "/remove/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteLekar(@PathVariable Long id) {
		Lekar lekar = lekarService.findOne(id);
		return lekarService.removeDoctor(id, lekar);
	}

	/*
	 * Search method, meant for handling received search parameters and
	 * returning expected data from database
	 */
	@GetMapping(value = "/pretraga/{pretraga}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<LekarDTO>> pretragaLekara(@PathVariable String pretraga, Pageable page) {
		Page<Lekar> lekari = lekarService.findAll(page);
		List<LekarDTO> retval = lekarService.pretraga(pretraga, lekari);

		return new ResponseEntity<>(retval, HttpStatus.OK);
	}
	
	/*
	 * Vacation/time-off request sending to administrator of clinic
	 */
	@PutMapping(value = "/odmor/{datumi}")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<LekarDTO> kreiranjeZahtevaZaOdmor(@PathVariable String datumi) {
		return lekarService.slanjeZahtevaZaOdmor(datumi);
	}
  
	@GetMapping(value = "/radnoVreme")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<String> getRadnoVreme() {
		return lekarService.getRadnoVreme();
	}

	@PutMapping(value = "/oceni/{id}")
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<LekarDTO> oceniLekara(@RequestBody LekarDTO lekarDTO, @PathVariable String id) {
		if (!lekarService.checkLekarConstraints(lekarDTO)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Lekar lekar = lekarService.preracunajOcenuLekara(lekarDTO);

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}
	
	/*
	 * Getting all appointments assigned to doctor
	 */
	@GetMapping(value = "/{doctorId}/pregledi")
	@PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN') or hasRole('NURSE')")
	public ResponseEntity<List<PregledDTO>> getPreglediDoktora(@PathVariable Long doctorId) {
		return lekarService.getDoctorAppointments(doctorId);
	}
	
	/*
	 * Next appointment/operation request sending to administrator of clinic
	 */
	@PostMapping(value = "/naredniTermin")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<ZahtevZaTerminDTO> kreiranjeZahtevaZaTermin(@RequestBody ZahtevZaTerminDTO zahtevDTO) {
		return lekarService.slanjeZahtevaZaTermin(zahtevDTO);
	}
  
}
