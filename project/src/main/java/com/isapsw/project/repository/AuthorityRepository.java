package com.isapsw.project.repository;

import com.isapsw.project.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName( String name );
}
