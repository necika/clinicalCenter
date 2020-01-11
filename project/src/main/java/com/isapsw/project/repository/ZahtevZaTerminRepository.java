package com.isapsw.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.isapsw.project.model.ZahtevZaTermin;

public interface ZahtevZaTerminRepository extends JpaRepository<ZahtevZaTermin, Long> {

	Page<ZahtevZaTermin> findAll(Pageable pageable);
	
}
