package com.isapsw.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.isapsw.project.model.ZahtevZaOdmor;

public interface ZahtevZaOdmorRepository extends JpaRepository<ZahtevZaOdmor, Long> {
	
	Page<ZahtevZaOdmor> findAll(Pageable pageable);
	
}
