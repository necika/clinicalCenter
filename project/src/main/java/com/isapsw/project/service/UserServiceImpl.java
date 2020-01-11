package com.isapsw.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isapsw.project.model.Authority;
import com.isapsw.project.model.Pacijent;
import com.isapsw.project.model.User;
import com.isapsw.project.model.UserRequest;
import com.isapsw.project.repository.PacijentRepository;
import com.isapsw.project.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PacijentRepository pacijentRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authorityService;

	@Override
	public User findById(Long id) throws AccessDeniedException {
		User u = userRepository.findById(id).orElseGet(null);
		return u;
	}

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() throws AccessDeniedException {
		List<User> result = userRepository.findAll();
		return result;
	}

	@Override
	public User save(UserRequest userRequest) {
		User u = new User();
		u.setUsername(userRequest.getEmail());
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		u.setIme(userRequest.getIme());
		u.setPrezime(userRequest.getPrezime());
		u.setEmail(userRequest.getEmail());
		u.setDrzava(userRequest.getDrzava());
		u.setGrad(userRequest.getGrad());
		u.setBrTelefona(userRequest.getBrTelefona());
		u.setAdresa(userRequest.getAdresa());
		u.setJedinstveniBrOsiguranika(userRequest.getJedinstveniBrOsiguranika());
		u.setEnabled(true);
		List<Authority> auth = authorityService.findByname("ROLE_PATIENT");
		u.setAuthorities(auth);
		u = this.userRepository.save(u);

		Pacijent pacijent = new Pacijent();
		pacijent.setJedinstveniBrOsiguranika(userRequest.getJedinstveniBrOsiguranika());
		this.pacijentRepository.save(pacijent);

		return u;
	}

	@Override
	public User findByJedinstveniBrOsiguranika(int jedinstveniBrOsiguranika) {
		return userRepository.findByJedinstveniBrOsiguranika(jedinstveniBrOsiguranika);
	}

	@Override
	public User save2(User user) {
		User u = new User();
		u.setUsername(user.getEmail());
		u.setPassword(passwordEncoder.encode(user.getPassword()));
		u.setIme(user.getIme());
		u.setPrezime(user.getPrezime());
		u.setEmail(user.getEmail());
		u.setDrzava(user.getDrzava());
		u.setGrad(user.getGrad());
		u.setBrTelefona(user.getBrTelefona());
		u.setAdresa(user.getAdresa());
		u.setJedinstveniBrOsiguranika(0);
		u.setEnabled(true);
		u.setOdobrenaRegistracija(user.getOdobrenaRegistracija());
		List<Authority> auth = authorityService.findByname("ROLE_PATIENT");
		u.setAuthorities(auth);
		u = this.userRepository.save(u);

		return u;
	}
}
