package com.isapsw.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.isapsw.project.model.TipPregleda;

public interface TipPregledaRepository extends JpaRepository<TipPregleda, Long> {

	Page<TipPregleda> findAll(Pageable pageable);

}
