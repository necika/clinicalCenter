package com.isapsw.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.isapsw.project.model.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Long> {

	Page<Pacijent> findAll(Pageable pageable);
	
	Pacijent findByJedinstveniBrOsiguranika(int jedinstveniBrOsiguranika);

//    List<Pacijent> findAllByIme(String ime);

//    List<Pacijent> findAllByPrezime(String prezime);

	@Query(value = "DELETE FROM users WHERE email = ?", nativeQuery = true)
	Void deleteUserFromUserByEmail(String email);

}
