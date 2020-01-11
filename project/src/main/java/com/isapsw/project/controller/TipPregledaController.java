package com.isapsw.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.isapsw.project.dto.TipPregledaDTO;
import com.isapsw.project.model.TipPregleda;
import com.isapsw.project.service.TipPregledaService;

@RestController
@RequestMapping(value = "isa/tipovi")
public class TipPregledaController {

	@Autowired
	private TipPregledaService tipPregledaService;

	/*
	 * Sending request for getting all the tuples in table TipPregleda and
	 * feeding it to the front-end part of application
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<TipPregledaDTO>> getTipovi() {
		return tipPregledaService.getAvailableTypes();
	}

	/*
	 * Sending request to database table TipPregleda for tuple specified by
	 * given ID
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TipPregledaDTO> getTip(@PathVariable Long id) {
		return tipPregledaService.getTypeById(id);
	}

	/*
	 * Receiving data from front-end part of application and injecting it as a
	 * new tuple in table Tip_Pregleda
	 */
	@PostMapping(value = "/add", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TipPregledaDTO> saveTipPregleda(@RequestBody TipPregledaDTO tipPregledaDTO) {
		return tipPregledaService.addType(tipPregledaDTO);
	}

	/*
	 * Receiving data from front-end part of application and updating the
	 * existing tuple in table TipPregleda
	 */
	@PutMapping(value = "/update/{id}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TipPregledaDTO> updateTip(@PathVariable Long id, @RequestBody TipPregledaDTO tipDTO) {
		return tipPregledaService.updateType(tipDTO);
	}

	/*
	 * Removing tuple selected by user on front-end part of application from
	 * database
	 */
	@DeleteMapping(value = "/remove/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteTip(@PathVariable Long id) {
		TipPregleda tip = tipPregledaService.findOne(id);
		return tipPregledaService.removeType(id, tip);
	}

	/*
	 * Search method, meant for handling received search parameters and
	 * returning expected data from database
	 */
	@GetMapping(value = "/pretraga/{pretraga}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<TipPregledaDTO>> pretragaTipova(@PathVariable String pretraga, Pageable page) {
		Page<TipPregleda> tipovi = tipPregledaService.findAll(page);
		List<TipPregledaDTO> retval = tipPregledaService.pretraga(pretraga, tipovi);
		return new ResponseEntity<>(retval, HttpStatus.OK);
	}

}
