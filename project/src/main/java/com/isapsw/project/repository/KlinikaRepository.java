package com.isapsw.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.isapsw.project.model.Klinika;



public interface KlinikaRepository extends JpaRepository<Klinika, Long>{

	Page<Klinika> findAll(Pageable pageable);

    List<Klinika> findAllByAdresa(String adresa);

    List<Klinika> findAllByNaziv(String naziv);

    List<Klinika> findAllByOcena(Float ocena);
	
}
