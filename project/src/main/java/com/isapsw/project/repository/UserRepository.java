package com.isapsw.project.repository;

import com.isapsw.project.dto.UserDTO;
import com.isapsw.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );

    User findByJedinstveniBrOsiguranika(int jedinstveniBrOsiguranika);

}
