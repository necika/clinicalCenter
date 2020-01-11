package com.isapsw.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isapsw.project.model.SifarnikDijagnozaILekova;



public interface SifarnikRepository extends JpaRepository<SifarnikDijagnozaILekova, Long>{

	SifarnikDijagnozaILekova findByLekovi(String lekovi);
	
}
