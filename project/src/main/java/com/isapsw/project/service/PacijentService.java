package com.isapsw.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.isapsw.project.dto.PacijentDTO;
import com.isapsw.project.dto.UserDTO;
import com.isapsw.project.model.Authority;
import com.isapsw.project.model.Pacijent;
import com.isapsw.project.model.User;
import com.isapsw.project.repository.PacijentRepository;
import com.isapsw.project.repository.UserRepository;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository pacijentRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityService authorityService;

	public Pacijent save(Pacijent pacijent) {
		return pacijentRepository.save(pacijent);
	}

	public Pacijent findOne(Long id) {
		return pacijentRepository.findById(id).orElseGet(null);
	}

	public List<Pacijent> findAll() {
		return pacijentRepository.findAll();
	}

	public Page<Pacijent> findAll(Pageable page) {
		return pacijentRepository.findAll(page);
	}
	
	public Pacijent findByJedinstveniBrOsiguranika(int broj) {
		return pacijentRepository.findByJedinstveniBrOsiguranika(broj);
	}

	public void remove(Long id) {
		pacijentRepository.deleteById(id);
	}

	// public List<Pacijent> findByAdresa(String adresa) {
	// return pacijentRepository.findAllByAdresa(adresa);
	// }

	// public List<Pacijent> findByIme(String ime) {
	// return pacijentRepository.findAllByIme(ime);
	// }

	// public List<Pacijent> findByPrezime(String prezime) {
	// return pacijentRepository.findAllByPrezime(prezime);
	// }

	/*
	 * Request to database for getting available tuples from database table Pacijent
	 */
	public ResponseEntity<List<PacijentDTO>> getAvailablePatients() {
		List<Pacijent> pacijenti = findAll();
		List<PacijentDTO> pacijentiDTO = new ArrayList<>();

		for (Pacijent p : pacijenti) {
			User u = userService.findByJedinstveniBrOsiguranika(p.getJedinstveniBrOsiguranika());
			p.setIme(u.getIme());
			p.setPrezime(u.getPrezime());
			p.setEmail(u.getEmail());
			p.setBrTelefona(u.getBrTelefona());
			p.setAdresa(u.getAdresa());
			p.setGrad(u.getGrad());
			p.setDrzava(u.getDrzava());
			p.setLozinka(u.getPassword());
			pacijentiDTO.add(new PacijentDTO(p));
		}

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}

	/*
	 * Request to database for getting tuple by given ID from database table
	 * Pacijent
	 */
	public ResponseEntity<PacijentDTO> getPatientById(Long id) {
		Pacijent pacijent = findOne(id);
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		User u = userService.findByJedinstveniBrOsiguranika(pacijent.getJedinstveniBrOsiguranika());
		pacijent.setIme(u.getIme());
		pacijent.setPrezime(u.getPrezime());
		pacijent.setEmail(u.getEmail());
		pacijent.setBrTelefona(u.getBrTelefona());
		pacijent.setAdresa(u.getAdresa());
		pacijent.setGrad(u.getGrad());
		pacijent.setDrzava(u.getDrzava());
		pacijent.setLozinka(u.getPassword());

		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}

	/*
	 * Service for updating personal information about patient
	 */
	public ResponseEntity<PacijentDTO> updatePatient(PacijentDTO pacijentDTO, String id) {
		if (pacijentDTO.getBrTelefona() == null || pacijentDTO.getAdresa() == null || pacijentDTO.getDrzava() == null
				|| pacijentDTO.getEmail() == null || pacijentDTO.getGrad() == null || pacijentDTO.getIme() == null
				|| pacijentDTO.getPrezime() == null || pacijentDTO.getLozinka() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Pacijent pacijent = findOne(pacijentDTO.getId());
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		pacijent.setJedinstveniBrOsiguranika(pacijentDTO.getJedinstveniBrOsiguranika());

		User u = userService.findByJedinstveniBrOsiguranika(pacijent.getJedinstveniBrOsiguranika());
		u.setUsername(pacijentDTO.getEmail());
		u.setIme(pacijentDTO.getIme());
		u.setPrezime(pacijentDTO.getPrezime());
		u.setEmail(pacijentDTO.getEmail());
		u.setDrzava(pacijentDTO.getDrzava());
		u.setGrad(pacijentDTO.getGrad());
		u.setBrTelefona(pacijentDTO.getBrTelefona());
		u.setAdresa(pacijentDTO.getAdresa());
		u.setJedinstveniBrOsiguranika(pacijentDTO.getJedinstveniBrOsiguranika());
		u.setEnabled(true);
		List<Authority> auth = authorityService.findByname("ROLE_DOCTOR");
		u.setAuthorities(auth);
		u = this.userRepository.save(u);

		pacijent = save(pacijent);

		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}

	public ResponseEntity<List<PacijentDTO>> getDisabledPatients() {

		List<Pacijent> pacijenti = findAll();
		List<PacijentDTO> pacijentiDTO = new ArrayList<>();

		for (Pacijent p : pacijenti) {

			User u = userService.findByJedinstveniBrOsiguranika(p.getJedinstveniBrOsiguranika());
			if (u.getOdobrenaRegistracija().equals("obrada")) {
				p.setIme(u.getIme());
				p.setPrezime(u.getPrezime());
				p.setEmail(u.getEmail());
				p.setBrTelefona(u.getBrTelefona());
				p.setAdresa(u.getAdresa());
				p.setGrad(u.getGrad());
				p.setDrzava(u.getDrzava());
				p.setLozinka(u.getPassword());
				pacijentiDTO.add(new PacijentDTO(p));
			}
		}

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}

	public ResponseEntity<List<PacijentDTO>> odbijanjePacijenta(String emailKomentar) {
		String[] niz = emailKomentar.split(",");

		try {
			String emailContent = "Razlog odbijanja : " + niz[0];
			emailService.sendNotificationAsync("kcentar4@gmail.com", emailContent);
			// debaguj ovo nesto nece
			User uu = userService.findById(Long.parseLong(niz[1]));
			uu.setOdobrenaRegistracija("odbijen");
			uu = userService.save2(uu);
			User u = userService.findByJedinstveniBrOsiguranika(0);
			userRepository.deleteById(u.getId());
			ResponseEntity<List<PacijentDTO>> pacijentiZaBack = getDisabledPatients();
			return pacijentiZaBack;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ResponseEntity<List<PacijentDTO>> prihvatanje(UserDTO userDTO) {
		try {
			String emailContent = "Korisnik " + userDTO.getIme() + " " + userDTO.getPrezime()
					+ " zeli da se registruje !\n\n" + "Da prihvatite kliknite na sledeci link:\n"
					+ "http://localhost:8080/resources/static/index.html#/pacijent/potvrdaPrekoEmaila/"
					+ userDTO.getEmail();
			emailService.sendNotificationAsync("kcentar4@gmail.com", emailContent);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void potvrdaPrekoEmailaNaBacku(String username) {
		User user = userService.findByUsername(username);
		user.setOdobrenaRegistracija("prihvacen");
		user = userService.save2(user);
		User uu = userService.findByJedinstveniBrOsiguranika(0);
		userRepository.deleteById(uu.getId());
	}

}
