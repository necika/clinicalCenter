package com.isapsw.project.service;

import com.isapsw.project.model.Authority;

import java.util.List;

public interface AuthorityService {
    List<Authority> findById(Long id);
    List<Authority> findByname(String name);
}
