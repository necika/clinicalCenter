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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isapsw.project.dto.KlinikaDTO;
import com.isapsw.project.dto.LekarDTO;
import com.isapsw.project.dto.SalaDTO;
import com.isapsw.project.model.Klinika;
import com.isapsw.project.service.KlinikaService;

@RestController
@RequestMapping(value = "isa/klinika")
public class KlinikaController {

	@Autowired
	private KlinikaService klinikaService;

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<List<KlinikaDTO>> getKlinike() {
		List<Klinika> klinike = klinikaService.findAll();
		List<KlinikaDTO> klinikeDTO = new ArrayList<>();
		for (Klinika k : klinike) {
			klinikeDTO.add(new KlinikaDTO(k));
		}

		return new ResponseEntity<>(klinikeDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/")
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<List<KlinikaDTO>> getKlinikePage(Pageable page) {
		Page<Klinika> klinike = klinikaService.findAll(page);
		List<KlinikaDTO> klinikeDTO = new ArrayList<>();
		for (Klinika k : klinike) {
			klinikeDTO.add(new KlinikaDTO(k));
		}

		return new ResponseEntity<>(klinikeDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/pretraga/{pretraga}")
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<List<KlinikaDTO>> pretragaKlinika(
			@PathVariable String pretraga, Pageable page) {
		Page<Klinika> klinike = klinikaService.findAll(page);
		List<KlinikaDTO> klinikeDTO = klinikaService.pretragaKlinike(pretraga,
				klinike);

		return new ResponseEntity<>(klinikeDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/dodavanje", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<KlinikaDTO> kreiranjeKlinike(
			@RequestBody KlinikaDTO klinikaDTO) {
		return klinikaService.addClinic(klinikaDTO);
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<KlinikaDTO> getKlinika(@PathVariable Long id) {
		return new ResponseEntity<>(new KlinikaDTO(klinikaService.findOne(id)),
				HttpStatus.OK);
	}

	@PutMapping(value = "/oceni/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<KlinikaDTO> oceniKliniku(
			@RequestBody KlinikaDTO klinikaDTO, @PathVariable String id) {
		if (!klinikaService.checkKlinikaConstraints(klinikaDTO)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Klinika klinika = klinikaService.preracunajOcenuKlinike(klinikaDTO);

		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}

	/*
	 * Getting all doctors employed at clinic
	 */
	@GetMapping(value = "/{clinicId}/lekari")
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<List<LekarDTO>> getDoktoriKlinike(
			@PathVariable Long clinicId) {
		return klinikaService.getClinicDoctors(clinicId);
	}

	/*
	 * Getting all ordinations of clinic
	 */
	@GetMapping(value = "/{clinicId}/sale")
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<List<SalaDTO>> getSaleKlinike(
			@PathVariable Long clinicId) {
		return klinikaService.getClinicOrdinations(clinicId);
	}

	/*
	 * Receiving data from front-end part of application and updating the
	 * existing tuple in table Klinika
	 */
	@PutMapping(value = "/update/{id}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<KlinikaDTO> updateKlinika(
			@RequestBody KlinikaDTO klinikaDTO) {
		return klinikaService.updateClinic(klinikaDTO);
	}

}
