package com.isapsw.project.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.isapsw.project.model.AdministratorKlinike;

public interface AdministratorKlinikeRepository extends JpaRepository<AdministratorKlinike, Long>{

	Page<AdministratorKlinike> findAll(Pageable pageable);
	
	@Query("select s from AdministratorKlinike s where s.klinika.id = ?1")
	Set<AdministratorKlinike> pronadjiPoIdKlinike(Long id);
	
	AdministratorKlinike findByJedinstveniBrOsiguranika(int jedinstveniBrOsiguranika);
	
}
