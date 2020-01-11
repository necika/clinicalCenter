package com.isapsw.project.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isapsw.project.dto.MedicinskaSestraDTO;
import com.isapsw.project.dto.ReceptDTO;
import com.isapsw.project.model.AdministratorKlinike;
import com.isapsw.project.model.Authority;
import com.isapsw.project.model.Klinika;
import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Recept;
import com.isapsw.project.model.User;
import com.isapsw.project.model.ZahtevZaOdmor;
import com.isapsw.project.repository.MedicinskaSestraRepository;
import com.isapsw.project.repository.UserRepository;

@Service
public class MedicinskaSestraService {

	@Autowired
	private MedicinskaSestraRepository sestraRepository;

	@Autowired
	private AdministratorKlinikeService adminService;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private ReceptService receptService;
	
	@Autowired
	private KlinikaService klinikaService;

	@Autowired
	private UserRepository userRepository;

	public MedicinskaSestra findByJedinstveniBrOsiguranika(int broj) {
		return sestraRepository.findByJedinstveniBrOsiguranika(broj);
	}
	
	@Autowired
	private UserService userService;

	@Autowired
	private ZahtevZaOdmorService zahtevService;

	public MedicinskaSestra save(MedicinskaSestra pacijent) {
		return sestraRepository.save(pacijent);
	}

	public MedicinskaSestra findOne(Long id) {
		return sestraRepository.findById(id).orElseGet(null);
	}

	public List<MedicinskaSestra> findAll() {
		return sestraRepository.findAll();
	}

	public void remove(Long id) {
		sestraRepository.deleteById(id);
	}

	public Page<MedicinskaSestra> findAll(Pageable page) {
		return sestraRepository.findAll(page);
	}

	/*
	 * Request to database for getting available tuples from database table
	 * MedicinskaSestra
	 */
	public ResponseEntity<List<MedicinskaSestraDTO>> getAvailableNurses() {
		List<MedicinskaSestra> sestre = findAll();
		List<MedicinskaSestraDTO> sestreDTO = new ArrayList<>();

		for (MedicinskaSestra s : sestre) {
			User u = userService.findByJedinstveniBrOsiguranika(s
					.getJedinstveniBrOsiguranika());
			s.setIme(u.getIme());
			s.setPrezime(u.getPrezime());
			s.setEmail(u.getEmail());
			s.setBrTelefona(u.getBrTelefona());
			s.setAdresa(u.getAdresa());
			s.setGrad(u.getGrad());
			s.setDrzava(u.getDrzava());
			s.setLozinka(u.getPassword());
			sestreDTO.add(new MedicinskaSestraDTO(s));
		}

		return new ResponseEntity<>(sestreDTO, HttpStatus.OK);
	}

	/*
	 * Request to database for getting tuple by given ID from database table
	 * MedicinskaSestra
	 */
	public ResponseEntity<MedicinskaSestraDTO> getNurseById(Long id) {
		MedicinskaSestra sestra = findOne(id);
		if (sestra == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		User u = userService.findByJedinstveniBrOsiguranika(sestra
				.getJedinstveniBrOsiguranika());
		sestra.setIme(u.getIme());
		sestra.setPrezime(u.getPrezime());
		sestra.setEmail(u.getEmail());
		sestra.setBrTelefona(u.getBrTelefona());
		sestra.setAdresa(u.getAdresa());
		sestra.setGrad(u.getGrad());
		sestra.setDrzava(u.getDrzava());
		sestra.setLozinka(u.getPassword());

		return new ResponseEntity<>(new MedicinskaSestraDTO(sestra),
				HttpStatus.OK);
	}

	/*
	 * Server-side validation of attributes used in class MedicinskaSestra
	 */
	public Boolean checkSestraConstraints(MedicinskaSestraDTO sestraDTO) {

		if (sestraDTO.getBrTelefona() == null || sestraDTO.getAdresa() == null
				|| sestraDTO.getDrzava() == null
				|| sestraDTO.getEmail() == null || sestraDTO.getGrad() == null
				|| sestraDTO.getIme() == null || sestraDTO.getPrezime() == null
				|| sestraDTO.getLozinka() == null) {
			return false;
		}

		return true;
	}

	/*
	 * Service for updating personal information about given nurse
	 */
	public MedicinskaSestra updateSestra(MedicinskaSestraDTO sestraDTO) {
		MedicinskaSestra sestra = findOne(sestraDTO.getId());
		sestra.setJedinstveniBrOsiguranika(sestraDTO
				.getJedinstveniBrOsiguranika());
		sestra.setOdobrenGodisnji(sestraDTO.getOdobrenGodisnji());
		sestra.setDatumOdlaskaNaGodisnji(sestraDTO.getDatumOdlaskaNaGodisnji());
		sestra.setDatumPovratkaSaGodisnjeg(sestraDTO
				.getDatumPovratkaSaGodisnjeg());
		sestra.setPocetakRadnogVremena(sestraDTO.getPocetakRadnogVremena());
		sestra.setKrajRadnogVremena(sestraDTO.getKrajRadnogVremena());
		sestra.setKlinika(sestraDTO.getKlinika());
		sestra = save(sestra);

		User u = userService.findByJedinstveniBrOsiguranika(sestra
				.getJedinstveniBrOsiguranika());
		u.setUsername(sestraDTO.getEmail());
		u.setIme(sestraDTO.getIme());
		u.setPrezime(sestraDTO.getPrezime());
		u.setEmail(sestraDTO.getEmail());
		u.setDrzava(sestraDTO.getDrzava());
		u.setGrad(sestraDTO.getGrad());
		u.setBrTelefona(sestraDTO.getBrTelefona());
		u.setAdresa(sestraDTO.getAdresa());
		u.setJedinstveniBrOsiguranika(sestraDTO.getJedinstveniBrOsiguranika());
		u.setEnabled(true);
		List<Authority> auth = authorityService.findByname("ROLE_NURSE");
		u.setAuthorities(auth);
		u = this.userRepository.save(u);

		return sestra;
	}

	/*
	 * Service for sending vacation/time-off request to administrator of clinic
	 */
	public ResponseEntity<MedicinskaSestraDTO> slanjeZahtevaZaOdmor(
			String datumi) {
		// treba samo da uzmem ulogovanu sestru
		MedicinskaSestra sestra = findOne((long) 1);
		User u = userService.findByJedinstveniBrOsiguranika(sestra
				.getJedinstveniBrOsiguranika());
		sestra.setIme(u.getIme());
		sestra.setPrezime(u.getPrezime());

		String[] request_query = datumi.split(",");
		String pocetniDatum = request_query[0];
		String krajnjiDatum = request_query[1];
		String tipZahteva = request_query[2];

		Date datumOdlaska = null;
		Date datumDolaska = null;
		try {
			datumOdlaska = new SimpleDateFormat("yyyy-MM-dd")
					.parse(pocetniDatum);
			datumDolaska = new SimpleDateFormat("yyyy-MM-dd")
					.parse(krajnjiDatum);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (request_query[0] == null || request_query[1] == null
				|| request_query[2] == "" || request_query[2] == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		/*
		 * Set<AdministratorKlinike> adminSet = sestra.getKlinika()
		 * .getAdministratorKlinike(); int indexForSending =
		 * generateIndex(adminSet); AdministratorKlinike admin = adminService
		 * .findOne((long) indexForSending);
		 */
		AdministratorKlinike admin = adminService.findOne((long) 1);
		if (admin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		ZahtevZaOdmor zahtev = new ZahtevZaOdmor(tipZahteva, datumOdlaska,
				datumDolaska, sestra.getIme() + " " + sestra.getPrezime(),
				sestra.getEmail());

		zahtev.setAdmin(admin);
		zahtev = zahtevService.save(zahtev);
		admin = adminService.save(admin);
		sestra = save(sestra);

		return new ResponseEntity<>(new MedicinskaSestraDTO(sestra),
				HttpStatus.OK);
	}

	public int generateIndex(Set<AdministratorKlinike> adminSet) {
		double randomDecimal = Math.random();
		randomDecimal = randomDecimal * adminSet.size() + 1;
		int randomInt = (int) randomDecimal;
		return randomInt;
	}

	public ResponseEntity<String> getRadnoVreme() {
		// uzeti uloogvanog
		MedicinskaSestra sestra = findOne((long) 1);

		String out = sestra.getPocetakRadnogVremena() + " - "
				+ sestra.getKrajRadnogVremena();

		return new ResponseEntity<>(out, HttpStatus.OK);
	}

	public ResponseEntity<MedicinskaSestraDTO> addNurse(String klinika,
			MedicinskaSestraDTO sestraDTO) {
		Long odabranaKlinika = (long) -1;
		try {
			odabranaKlinika = Long.parseLong(klinika);
		} catch (Exception e) {
			odabranaKlinika = (long) -1;
		}

		if (sestraDTO.getBrTelefona().equals("")
				|| sestraDTO.getBrTelefona() == null
				|| sestraDTO.getAdresa() == null
				|| sestraDTO.getDrzava() == null
				|| sestraDTO.getEmail() == null || sestraDTO.getGrad() == null
				|| sestraDTO.getIme() == null || sestraDTO.getLozinka() == null
				|| sestraDTO.getPrezime() == null
				|| sestraDTO.getJedinstveniBrOsiguranika() <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		int pocetakSat = Integer.parseInt(sestraDTO.getPocetakRadnogVremena()
				.split(":")[0]);
		int pocetakMinut = Integer.parseInt(sestraDTO.getPocetakRadnogVremena()
				.split(":")[1]);

		int krajSat = Integer.parseInt(sestraDTO.getKrajRadnogVremena().split(
				":")[0]);
		int krajMinut = Integer.parseInt(sestraDTO.getKrajRadnogVremena()
				.split(":")[1]);

		if (pocetakSat < 0 || pocetakSat >= 25 || krajSat < 0 || krajSat >= 25
				|| pocetakMinut < 0 || pocetakMinut >= 60 || krajMinut < 0
				|| krajMinut >= 60) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		MedicinskaSestra sestra = new MedicinskaSestra();
		sestra.setPocetakRadnogVremena(sestraDTO.getPocetakRadnogVremena());
		sestra.setKrajRadnogVremena(sestraDTO.getKrajRadnogVremena());
		sestra.setJedinstveniBrOsiguranika(sestraDTO
				.getJedinstveniBrOsiguranika());
		sestra.setOdobrenGodisnji(sestraDTO.getOdobrenGodisnji());
		sestra.setDatumOdlaskaNaGodisnji(sestraDTO.getDatumOdlaskaNaGodisnji());
		sestra.setDatumPovratkaSaGodisnjeg(sestraDTO
				.getDatumPovratkaSaGodisnjeg());

		Klinika kl = klinikaService.findOne(odabranaKlinika);
		if (kl == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		sestra.setKlinika(kl);
		sestra = save(sestra);

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User u = new User();
		u.setUsername(sestraDTO.getEmail());
		u.setPassword(passwordEncoder.encode(sestraDTO.getLozinka()));
		u.setIme(sestraDTO.getIme());
		u.setPrezime(sestraDTO.getPrezime());
		u.setEmail(sestraDTO.getEmail());
		u.setDrzava(sestraDTO.getDrzava());
		u.setGrad(sestraDTO.getGrad());
		u.setBrTelefona(sestraDTO.getBrTelefona());
		u.setAdresa(sestraDTO.getAdresa());
		u.setJedinstveniBrOsiguranika(sestraDTO.getJedinstveniBrOsiguranika());
		u.setEnabled(true);
		List<Authority> auth = authorityService.findByname("ROLE_NURSE");
		u.setAuthorities(auth);
		u = this.userRepository.save(u);

		return new ResponseEntity<>(new MedicinskaSestraDTO(sestra),
				HttpStatus.CREATED);
	}

	public ResponseEntity<Void> removeNurse(Long id, MedicinskaSestra sestra) {
		if (sestra != null && sestra.getPregledi().size() == 0) {
			System.out.println("usao ovde");
			User u = userService.findByJedinstveniBrOsiguranika(sestra
					.getJedinstveniBrOsiguranika());
			deleteFromJointUserAuthTable(u.getId());
			deleteFromUserTable(u.getId());
			remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<Void> overaReceptaService(Long id) {
		Recept recept = receptService.findOne(id);
		if(recept == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		recept.setOveren(true);
		recept = receptService.save(recept);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public void deleteFromJointUserAuthTable(Long key) {
		String SQL_DELETE = "DELETE FROM USER_AUTHORITY WHERE USER_ID=?";

		try (Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/ccentre", "postgres", "root");
				PreparedStatement preparedStatement = connection
						.prepareStatement(SQL_DELETE)) {
			preparedStatement.setLong(1, key);
			int row = preparedStatement.executeUpdate();
			System.out.println("Rows affected: " + row);
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(),
					e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFromUserTable(Long key) {
		String SQL_DELETE = "DELETE FROM USERS WHERE ID=?";

		try (Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/ccentre", "postgres", "root");
				PreparedStatement preparedStatement = connection
						.prepareStatement(SQL_DELETE)) {
			preparedStatement.setLong(1, key);
			int row = preparedStatement.executeUpdate();
			System.out.println("Rows affected: " + row);
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(),
					e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResponseEntity<List<ReceptDTO>> getReceptiZaOveruService(Authentication authentication) {
		User user = userService.findByUsername(authentication.getName());
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		MedicinskaSestra sestra = findByJedinstveniBrOsiguranika(user.getJedinstveniBrOsiguranika());
		if(sestra == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<Recept> recepti = receptService.findAll();
		List<ReceptDTO> receptiDTO = new ArrayList<>();
		for(Recept r : recepti) {
			if(r.getSestra().getId() == sestra.getId()) {
				receptiDTO.add(new ReceptDTO(r));
			}
		}
		
		
		return new ResponseEntity<>(receptiDTO, HttpStatus.OK);
	}

}
