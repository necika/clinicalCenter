package com.isapsw.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.isapsw.project.dto.TipPregledaDTO;
import com.isapsw.project.model.TipPregleda;
import com.isapsw.project.repository.TipPregledaRepository;

@Service
public class TipPregledaService {

	@Autowired
	private TipPregledaRepository tipPregledaRepository;

	public TipPregleda findOne(Long id) {
		return tipPregledaRepository.findById(id).orElseGet(null);
	}

	public List<TipPregleda> findAll() {
		return tipPregledaRepository.findAll();
	}

	public Page<TipPregleda> findAll(Pageable page) {
		return tipPregledaRepository.findAll(page);
	}

	public TipPregleda save(TipPregleda tipPregleda) {
		return tipPregledaRepository.save(tipPregleda);
	}

	public void remove(Long id) {
		tipPregledaRepository.deleteById(id);
	}
	
	/*
	 * Request to database for getting all available tuples from database table TipPregleda
	 */
	public ResponseEntity<List<TipPregledaDTO>> getAvailableTypes() {
		List<TipPregleda> tipovi = findAll();
		List<TipPregledaDTO> tipoviDTO = new ArrayList<>();
		for (TipPregleda t : tipovi) {
			tipoviDTO.add(new TipPregledaDTO(t));
		}

		return new ResponseEntity<>(tipoviDTO, HttpStatus.OK);
	}
	
	/*
	 * Request to database for getting specific tuple by given ID from database table TipPregleda
	 */
	public ResponseEntity<TipPregledaDTO> getTypeById(Long id) {
		TipPregleda tip = findOne(id);
		if (tip == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new TipPregledaDTO(tip), HttpStatus.OK);
	}

	/*
	 * Logic for addition of a new tuple to database table TipPregleda
	 */
	public ResponseEntity<TipPregledaDTO> addType(TipPregledaDTO tipPregledaDTO) {
		if (tipPregledaDTO.getNaziv() == ""
				|| tipPregledaDTO.getNaziv() == null
				|| tipPregledaDTO.getOpis() == ""
				|| tipPregledaDTO.getOpis() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		TipPregleda tipPregleda = new TipPregleda();
		tipPregleda.setNaziv(tipPregledaDTO.getNaziv());
		tipPregleda.setOpis(tipPregledaDTO.getOpis());
		tipPregleda = save(tipPregleda);

		return new ResponseEntity<>(new TipPregledaDTO(tipPregleda), HttpStatus.CREATED);
	}
	
	/*
	 * Logic for updating of an existing tuple from database table TipPregleda
	 */
	public ResponseEntity<TipPregledaDTO> updateType(TipPregledaDTO tipDTO) {
		TipPregleda tip = findOne(tipDTO.getId());
		if (tip == null || tipDTO.getNaziv().equals("") || tipDTO.getNaziv() == null
				|| tipDTO.getOpis() == null || tipDTO.getOpis().equals("")
				|| tipDTO.getPregledi().size() > 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		tip.setId(tipDTO.getId());
		tip.setNaziv(tipDTO.getNaziv());
		tip.setOpis(tipDTO.getOpis());
		tip = save(tip);
		
		return new ResponseEntity<>(new TipPregledaDTO(tip), HttpStatus.OK);
	}
	
	/*
	 * Logic for removing of an existing tuple in database table TipPregleda
	 */
	public ResponseEntity<Void> removeType(Long id, TipPregleda tip) {
		if (tip != null && tip.getPregledi().size() == 0) {
			remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Search algorithm for entity TipPregleda
	 */
	public List<TipPregledaDTO> pretraga(String search_query,
			Page<TipPregleda> tipovi) {

		List<TipPregledaDTO> retval = new ArrayList<>();
		String parameter_array[] = search_query.split(",");
		String naziv;
		String opis;

		if (parameter_array[0].equals("") || parameter_array[0] == null) {
			naziv = "";
		} else {
			naziv = parameter_array[0];
		}

		if (parameter_array[1].equals("") || parameter_array[1] == null) {
			opis = "";
		} else {
			opis = parameter_array[1];
		}

		for (TipPregleda t : tipovi) {
			Boolean flagNaziv = false;
			Boolean flagOpis = false;

			if (!naziv.equals("")) {
				if (t.getNaziv().toLowerCase().contains(naziv.toLowerCase())) {
					flagNaziv = true;
				} else {
					flagNaziv = false;
				}
			} else {
				flagNaziv = true;
			}

			if (!opis.equals("")) {
				if (t.getOpis().toLowerCase().contains(opis.toLowerCase())) {
					flagOpis = true;
				} else {
					flagOpis = false;
				}
			} else {
				flagOpis = true;
			}

			if (flagNaziv && flagOpis) {
				retval.add(new TipPregledaDTO(t));
			}
		}

		return retval;
	}
}
