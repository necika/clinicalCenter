package com.isapsw.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.isapsw.project.model.Pregled;

import java.util.List;

public interface PregledRepository extends JpaRepository<Pregled, Long> {
	
	Page<Pregled> findAll(Pageable pageable);

	List<Pregled> findAllByRezervisan(Boolean rezervisan);

}
