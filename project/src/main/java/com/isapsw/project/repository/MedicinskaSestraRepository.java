package com.isapsw.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Pacijent;

public interface MedicinskaSestraRepository extends JpaRepository<MedicinskaSestra, Long>  {
	
	
	MedicinskaSestra findByJedinstveniBrOsiguranika(int jedinstveniBrOsiguranika);
}
