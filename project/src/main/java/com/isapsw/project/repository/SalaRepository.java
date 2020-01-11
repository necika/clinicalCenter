package com.isapsw.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.isapsw.project.model.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long> {

	Page<Sala> findAll(Pageable pageable);
	
}
