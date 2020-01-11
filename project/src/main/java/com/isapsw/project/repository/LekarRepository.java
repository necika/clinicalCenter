package com.isapsw.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.Pacijent;

public interface LekarRepository extends JpaRepository<Lekar, Long> {

    Page<Lekar> findAll(Pageable pageable);

    List<Lekar> findAllByOcena(Float ocena);
    
    Lekar findByJedinstveniBrOsiguranika(int jedinstveniBrOsiguranika);

}
