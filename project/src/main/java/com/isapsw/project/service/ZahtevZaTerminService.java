package com.isapsw.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isapsw.project.model.ZahtevZaTermin;
import com.isapsw.project.repository.ZahtevZaTerminRepository;

@Service
public class ZahtevZaTerminService {

	@Autowired
	private ZahtevZaTerminRepository zahtevRepository;
	
	public ZahtevZaTermin findOne(Long id) {
		return zahtevRepository.findById(id).orElseGet(null);
	}

	public List<ZahtevZaTermin> findAll() {
		return zahtevRepository.findAll();
	}

	public Page<ZahtevZaTermin> findAll(Pageable page) {
		return zahtevRepository.findAll(page);
	}

	public ZahtevZaTermin save(ZahtevZaTermin zahtev) {
		return zahtevRepository.save(zahtev);
	}

	public void remove(Long id) {
		zahtevRepository.deleteById(id);
	}
	
}
