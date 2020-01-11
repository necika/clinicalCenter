package com.isapsw.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isapsw.project.model.Recept;

public interface ReceptRepository extends JpaRepository<Recept, Long>{
	
}
