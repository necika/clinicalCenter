package com.isapsw.project.service;

import java.util.List;

import com.isapsw.project.model.User;
import com.isapsw.project.model.UserRequest;

public interface UserService {
	User findById(Long id);

	User findByUsername(String username);

	User findByJedinstveniBrOsiguranika(int jedinstveniBrOsiguranika);

	List<User> findAll();

	User save(UserRequest userRequest);

	User save2(User user);
}
