package com.isapsw.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isapsw.project.model.ZahtevZaOdmor;
import com.isapsw.project.repository.ZahtevZaOdmorRepository;

@Service
public class ZahtevZaOdmorService {

	@Autowired
	private ZahtevZaOdmorRepository zahtevRepository;
	
	public ZahtevZaOdmor findOne(Long id) {
		return zahtevRepository.findById(id).orElseGet(null);
	}

	public List<ZahtevZaOdmor> findAll() {
		return zahtevRepository.findAll();
	}

	public Page<ZahtevZaOdmor> findAll(Pageable page) {
		return zahtevRepository.findAll(page);
	}

	public ZahtevZaOdmor save(ZahtevZaOdmor zahtev) {
		return zahtevRepository.save(zahtev);
	}

	public void remove(Long id) {
		zahtevRepository.deleteById(id);
	}
	
}
