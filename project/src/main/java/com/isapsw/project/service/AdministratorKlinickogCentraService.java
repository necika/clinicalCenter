package com.isapsw.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isapsw.project.dto.AdministratorKlinickogCentraDTO;
import com.isapsw.project.model.AdministratorKlinickogCentra;
import com.isapsw.project.model.Authority;
import com.isapsw.project.model.User;
import com.isapsw.project.repository.AdministratorKlinickogCentraRepository;
import com.isapsw.project.repository.UserRepository;


@Service
public class AdministratorKlinickogCentraService {

	@Autowired
	private AdministratorKlinickogCentraRepository adminRepository;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	public List<AdministratorKlinickogCentra> findAll() {
		return adminRepository.findAll();
	}

	public Page<AdministratorKlinickogCentra> findAll(Pageable page) {
		return adminRepository.findAll(page);
	}

	public AdministratorKlinickogCentra save(AdministratorKlinickogCentra admin) {
		return adminRepository.save(admin);
	}

	public AdministratorKlinickogCentra findOne(Long id) {
		return adminRepository.findById(id).orElseGet(null);
	}
	
	/*
	 * Request to database for getting available tuples from database table AdministratorKlinickogCentra
	 */
	public ResponseEntity<List<AdministratorKlinickogCentraDTO>> getAvailableAdmins() {
		List<AdministratorKlinickogCentra> administratoriCentra = findAll();
		List<AdministratorKlinickogCentraDTO> administratoriCentraDTO = new ArrayList<>();

		for (AdministratorKlinickogCentra a : administratoriCentra) {
			User u = userService.findByJedinstveniBrOsiguranika(a
					.getJedinstveniBrOsiguranika());
			a.setIme(u.getIme());
			a.setPrezime(u.getPrezime());
			a.setEmail(u.getEmail());
			a.setBrTelefona(u.getBrTelefona());
			a.setAdresa(u.getAdresa());
			a.setGrad(u.getGrad());
			a.setDrzava(u.getDrzava());
			a.setLozinka(u.getPassword());
			administratoriCentraDTO.add(new AdministratorKlinickogCentraDTO(a));
		}

		return new ResponseEntity<>(administratoriCentraDTO, HttpStatus.OK);
	}
	
	/*
	 * Request to database for getting tuple by given ID from database table AdministratorKlinickogCentra
	 */
	public ResponseEntity<AdministratorKlinickogCentraDTO> getAdminById(Long id) {
		AdministratorKlinickogCentra adminCentra = findOne(id);
		if (adminCentra == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		User u = userService.findByJedinstveniBrOsiguranika(adminCentra.getJedinstveniBrOsiguranika());
		adminCentra.setIme(u.getIme());
		adminCentra.setPrezime(u.getPrezime());
		adminCentra.setEmail(u.getEmail());
		adminCentra.setBrTelefona(u.getBrTelefona());
		adminCentra.setAdresa(u.getAdresa());
		adminCentra.setGrad(u.getGrad());
		adminCentra.setDrzava(u.getDrzava());
		adminCentra.setLozinka(u.getPassword());
		
		return new ResponseEntity<>(new AdministratorKlinickogCentraDTO(adminCentra), HttpStatus.OK);
	}
	
	/*
	 * Logic for addition of a new tuple to database table AdministratorKlinickogCentra
	 */
	public ResponseEntity<AdministratorKlinickogCentraDTO> addAdmin(AdministratorKlinickogCentraDTO adminDTO) {
		
		if (adminDTO.getBrTelefona() == null || adminDTO.getAdresa() == null || adminDTO.getDrzava() == null
				|| adminDTO.getEmail() == null || adminDTO.getGrad() == null || adminDTO.getIme() == null
				|| adminDTO.getLozinka() == null || adminDTO.getPrezime() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		AdministratorKlinickogCentra adminSave = new AdministratorKlinickogCentra();
		adminSave.setJedinstveniBrOsiguranika(adminDTO.getJedinstveniBrOsiguranika());
		adminSave = save(adminSave);
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User u = new User();
		u.setUsername(adminDTO.getEmail());
		u.setPassword(passwordEncoder.encode(adminDTO.getLozinka()));
		u.setIme(adminDTO.getIme());
		u.setPrezime(adminDTO.getPrezime());
		u.setEmail(adminDTO.getEmail());
		u.setDrzava(adminDTO.getDrzava());
		u.setGrad(adminDTO.getGrad());
		u.setBrTelefona(adminDTO.getBrTelefona());
		u.setAdresa(adminDTO.getAdresa());
		u.setJedinstveniBrOsiguranika(adminDTO.getJedinstveniBrOsiguranika());
		u.setEnabled(true);
		List<Authority> auth = authorityService.findByname("ROLE_ADMIN");
		u.setAuthorities(auth);
		u = this.userRepository.save(u);
		
		return new ResponseEntity<>(new AdministratorKlinickogCentraDTO(adminSave), HttpStatus.CREATED);
	}
	
	/*
	 * Password updating for given administrator of clinic centre
	 */
	public ResponseEntity<AdministratorKlinickogCentraDTO> changePassword(String lozinka) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//ovde treba da uzmem ulogovanog
		AdministratorKlinickogCentra acc = findOne((long) 1);
		if (lozinka.equals("") || lozinka == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String encodedPassword = passwordEncoder.encode(lozinka);

		acc.setLozinka(encodedPassword);
		acc.setPromenjenaLozinka(true);
		acc = save(acc);
		
		return new ResponseEntity<>(new AdministratorKlinickogCentraDTO(acc), HttpStatus.OK);
	}
	
}
