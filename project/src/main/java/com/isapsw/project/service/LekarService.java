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
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isapsw.project.dto.LekarDTO;
import com.isapsw.project.dto.PregledDTO;
import com.isapsw.project.dto.ZahtevZaTerminDTO;
import com.isapsw.project.model.AdministratorKlinike;
import com.isapsw.project.model.Authority;
import com.isapsw.project.model.Klinika;
import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Pacijent;
import com.isapsw.project.model.Pregled;
import com.isapsw.project.model.User;
import com.isapsw.project.model.ZahtevZaOdmor;
import com.isapsw.project.model.ZahtevZaTermin;
import com.isapsw.project.repository.LekarRepository;
import com.isapsw.project.repository.UserRepository;

@Service
public class LekarService {

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private LekarRepository lekarRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdministratorKlinikeService adminService;

	@Autowired
	private KlinikaService klinikaService;

	@Autowired
	private UserService userService;

	@Autowired
	private ZahtevZaOdmorService zahtevService;
	
	@Autowired
	private ZahtevZaTerminService zahtevTerminService;
	
	@Autowired
	private EmailService emailService;

	public Lekar findOne(Long id) {
		return lekarRepository.findById(id).orElseGet(null);
	}

	public Lekar save(Lekar lekar) {
		return lekarRepository.save(lekar);
	}

	public Lekar findByJedinstveniBrOsiguranika(int broj) {
		return lekarRepository.findByJedinstveniBrOsiguranika(broj);
	}
	
	public List<Lekar> findAll() {
		return lekarRepository.findAll();
	}

	public Page<Lekar> findAll(Pageable page) {
		return lekarRepository.findAll(page);
	}

	public List<Lekar> findAllByOcena(Float ocena) {
		return lekarRepository.findAllByOcena(ocena);
	}

	public void remove(Long id) {
		lekarRepository.deleteById(id);
	}

	/*
	 * Request to database for getting available tuples from database table
	 * Lekar
	 */
	public ResponseEntity<List<LekarDTO>> getAvailableDoctors() {
		List<Lekar> lekari = findAll();
		List<LekarDTO> lekariDTO = new ArrayList<>();

		for (Lekar l : lekari) {
			User u = userService.findByJedinstveniBrOsiguranika(l.getJedinstveniBrOsiguranika());
			l.setIme(u.getIme());
			l.setPrezime(u.getPrezime());
			l.setEmail(u.getEmail());
			l.setBrTelefona(u.getBrTelefona());
			l.setAdresa(u.getAdresa());
			l.setGrad(u.getGrad());
			l.setDrzava(u.getDrzava());
			l.setLozinka(u.getPassword());
			lekariDTO.add(new LekarDTO(l));
		}

		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}

	/*
	 * Request to database for getting tuple by given ID from database table
	 * Lekar
	 */
	public ResponseEntity<LekarDTO> getDoctorById(Long id) {
		Lekar lekar = findOne(id);
		if (lekar == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		User u = userService.findByJedinstveniBrOsiguranika(lekar
				.getJedinstveniBrOsiguranika());
		lekar.setIme(u.getIme());
		lekar.setPrezime(u.getPrezime());
		lekar.setEmail(u.getEmail());
		lekar.setBrTelefona(u.getBrTelefona());
		lekar.setAdresa(u.getAdresa());
		lekar.setGrad(u.getGrad());
		lekar.setDrzava(u.getDrzava());
		lekar.setLozinka(u.getPassword());

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}

	/*
	 * Logic for addition of a new tuple to database table Lekar
	 */
	public ResponseEntity<LekarDTO> addDoctor(String klinika, LekarDTO lekarDTO) {
		Long odabranaKlinika = (long) -1;
		try {
			odabranaKlinika = Long.parseLong(klinika);
		} catch (Exception e) {
			odabranaKlinika = (long) -1;
		}

		if (lekarDTO.getBrTelefona().equals("")
				|| lekarDTO.getBrTelefona() == null
				|| lekarDTO.getAdresa() == null || lekarDTO.getDrzava() == null
				|| lekarDTO.getEmail() == null || lekarDTO.getGrad() == null
				|| lekarDTO.getIme() == null || lekarDTO.getLozinka() == null
				|| lekarDTO.getPrezime() == null
				|| lekarDTO.getJedinstveniBrOsiguranika() <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		int pocetakSat = Integer.parseInt(lekarDTO.getPocetakRadnogVremena()
				.split(":")[0]);
		int pocetakMinut = Integer.parseInt(lekarDTO.getPocetakRadnogVremena()
				.split(":")[1]);

		int krajSat = Integer.parseInt(lekarDTO.getKrajRadnogVremena().split(
				":")[0]);
		int krajMinut = Integer.parseInt(lekarDTO.getKrajRadnogVremena().split(
				":")[1]);

		if (pocetakSat < 0 || pocetakSat >= 25 || krajSat < 0 || krajSat >= 25
				|| pocetakMinut < 0 || pocetakMinut >= 60 || krajMinut < 0
				|| krajMinut >= 60) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Lekar lekar = new Lekar();
		lekar.setOcena(lekarDTO.getOcena());
		lekar.setPocetakRadnogVremena(lekarDTO.getPocetakRadnogVremena());
		lekar.setKrajRadnogVremena(lekarDTO.getKrajRadnogVremena());
		lekar.setJedinstveniBrOsiguranika(lekarDTO
				.getJedinstveniBrOsiguranika());

		Klinika kl = klinikaService.findOne(odabranaKlinika);
		if (kl == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		lekar.setKlinika(kl);
//		lekar = save(lekar);

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User u = new User();
		u.setUsername(lekarDTO.getEmail());
		u.setPassword(passwordEncoder.encode(lekarDTO.getLozinka()));
		u.setIme(lekarDTO.getIme());
		u.setPrezime(lekarDTO.getPrezime());
		u.setEmail(lekarDTO.getEmail());
		u.setDrzava(lekarDTO.getDrzava());
		u.setGrad(lekarDTO.getGrad());
		u.setBrTelefona(lekarDTO.getBrTelefona());
		u.setAdresa(lekarDTO.getAdresa());
		u.setJedinstveniBrOsiguranika(lekarDTO.getJedinstveniBrOsiguranika());
		u.setEnabled(true);
		List<Authority> auth = authorityService.findByname("ROLE_DOCTOR");
		u.setAuthorities(auth);
		u = this.userRepository.save(u);

		lekar.setUser(u);
		lekar = save(lekar);

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.CREATED);
	}

	/*
	 * Logic for removing tuple from database table Lekar by given id
	 */
	public ResponseEntity<Void> removeDoctor(Long id, Lekar lekar) {
		if (lekar != null && lekar.getPregledi().size() == 0) {
			User u = userService.findByJedinstveniBrOsiguranika(lekar
					.getJedinstveniBrOsiguranika());
			deleteFromJointUserAuthTable(u.getId());
			deleteFromUserTable(u.getId());
			remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
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

	/*
	 * Search algorithm for entity Lekar
	 */
	public List<LekarDTO> pretraga(String search_query, Page<Lekar> lekari) {

		List<LekarDTO> retval = new ArrayList<>();
		String parameter_array[] = search_query.split(",");
		String ime;
		String prezime;
		Float minOcena;
		Float maxOcena;

		if (parameter_array[0].equals("") || parameter_array[0] == null) {
			ime = "";
		} else {
			ime = parameter_array[0];
		}

		if (parameter_array[1].equals("") || parameter_array[1] == null) {
			prezime = "";
		} else {
			prezime = parameter_array[1];
		}

		try {
			minOcena = Float.parseFloat(parameter_array[2]);
		} catch (NumberFormatException e) {
			minOcena = (float) -1;
		}

		try {
			maxOcena = Float.parseFloat(parameter_array[3]);
		} catch (NumberFormatException e) {
			maxOcena = (float) -1;
		}

		for (Lekar l : lekari) {
			User u = userService.findByJedinstveniBrOsiguranika(l
					.getJedinstveniBrOsiguranika());
			Boolean flagIme = false;
			Boolean flagPrezime = false;
			Boolean flagOcena = false;

			if (!ime.equals("")) {
				if (u.getIme().toLowerCase().contains(ime.toLowerCase())) {
					flagIme = true;
				} else {
					flagIme = false;
				}
			} else {
				flagIme = true;
			}

			if (!prezime.equals("")) {
				if (u.getPrezime().toLowerCase()
						.contains(prezime.toLowerCase())) {
					flagPrezime = true;
				} else {
					flagPrezime = false;
				}
			} else {
				flagPrezime = true;
			}

			if (minOcena != -1 && maxOcena != -1) {
				if (l.getOcena() >= minOcena && l.getOcena() <= maxOcena) {
					flagOcena = true;
				} else {
					flagOcena = false;
				}
			} else if (minOcena != -1 && maxOcena == -1) {
				if (l.getOcena() >= minOcena) {
					flagOcena = true;
				} else {
					flagOcena = false;
				}
			} else if (minOcena == -1 && maxOcena != -1) {
				if (l.getOcena() <= maxOcena) {
					flagOcena = true;
				} else {
					flagOcena = false;
				}
			} else {
				flagOcena = true;
			}

			if (flagIme && flagPrezime && flagOcena) {
				l.setIme(u.getIme());
				l.setPrezime(u.getPrezime());
				l.setAdresa(u.getAdresa());
				l.setDrzava(u.getDrzava());
				l.setGrad(u.getGrad());
				l.setBrTelefona(u.getBrTelefona());
				l.setEmail(u.getEmail());
				l.setLozinka(u.getPassword());
				retval.add(new LekarDTO(l));
			}
		}

		return retval;
	}

	/*
	 * Method for generating random index of clinic administrator, to which the
	 * vacation/time-off request will be sent
	 */
	public int generateIndex(Set<AdministratorKlinike> adminSet) {
		double randomDecimal = Math.random();
		randomDecimal = randomDecimal * adminSet.size() + 1;
		int randomInt = (int) randomDecimal;

		return randomInt;
	}

	/*
	 * Sending vacation/time-off request to administrator of clinic
	 */
	public ResponseEntity<LekarDTO> slanjeZahtevaZaOdmor(String datumi) {
		Lekar lekar = findOne((long) 1);
		User u = userService.findByJedinstveniBrOsiguranika(lekar
				.getJedinstveniBrOsiguranika());
		lekar.setIme(u.getIme());
		lekar.setPrezime(u.getPrezime());
		lekar.setEmail(u.getEmail());

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

		AdministratorKlinike admin = adminService.findOne((long) 1);

		if (admin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		ZahtevZaOdmor zahtev = new ZahtevZaOdmor(tipZahteva, datumOdlaska,
				datumDolaska, lekar.getIme() + " " + lekar.getPrezime(), lekar.getEmail());
		zahtev.setAdmin(admin);
		zahtev = zahtevService.save(zahtev);
		lekar = save(lekar);
		admin = adminService.save(admin);

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}

	public ResponseEntity<String> getRadnoVreme() {
		// uzeti uloogvanog
		Lekar lekar = findOne((long) 1);

		String out = lekar.getPocetakRadnogVremena() + " - "
				+ lekar.getKrajRadnogVremena();

		return new ResponseEntity<>(out, HttpStatus.OK);
	}

	/*
	 * Server-side validation of attributes used in class Lekar
	 */
	public Boolean checkLekarConstraints(LekarDTO lekarDTO) {

		if (lekarDTO.getBrTelefona().equals("")
				|| lekarDTO.getBrTelefona() == null
				|| lekarDTO.getAdresa() == null || lekarDTO.getDrzava() == null
				|| lekarDTO.getEmail() == null || lekarDTO.getGrad() == null
				|| lekarDTO.getIme() == null || lekarDTO.getPrezime() == null
				|| lekarDTO.getLozinka() == null) {
			return false;
		}

		return true;
	}

	/*
	 * Service for updating personal information about given doctor
	 */
	public ResponseEntity<LekarDTO> updateDoctor(LekarDTO lekarDTO) {
		if (lekarDTO.getBrTelefona() == null || lekarDTO.getAdresa() == null
				|| lekarDTO.getDrzava() == null || lekarDTO.getEmail() == null
				|| lekarDTO.getGrad() == null || lekarDTO.getIme() == null
				|| lekarDTO.getLozinka() == null
				|| lekarDTO.getPrezime() == null
				|| lekarDTO.getPocetakRadnogVremena() == null
				|| lekarDTO.getKrajRadnogVremena() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Lekar lekar = findOne(lekarDTO.getId());
		lekar.setKlinika(lekarDTO.getKlinika());
		lekar.setOcena(lekarDTO.getOcena());
		lekar.setPocetakRadnogVremena(lekarDTO.getPocetakRadnogVremena());
		lekar.setKrajRadnogVremena(lekarDTO.getKrajRadnogVremena());
		lekar.setJedinstveniBrOsiguranika(lekarDTO
				.getJedinstveniBrOsiguranika());
		lekar = save(lekar);

		User u = userService.findByJedinstveniBrOsiguranika(lekar
				.getJedinstveniBrOsiguranika());
		u.setUsername(lekarDTO.getEmail());
		u.setIme(lekarDTO.getIme());
		u.setPrezime(lekarDTO.getPrezime());
		u.setEmail(lekarDTO.getEmail());
		u.setDrzava(lekarDTO.getDrzava());
		u.setGrad(lekarDTO.getGrad());
		u.setBrTelefona(lekarDTO.getBrTelefona());
		u.setAdresa(lekarDTO.getAdresa());
		u.setJedinstveniBrOsiguranika(lekarDTO.getJedinstveniBrOsiguranika());
		u.setEnabled(true);
		List<Authority> auth = authorityService.findByname("ROLE_DOCTOR");
		u.setAuthorities(auth);
		u = this.userRepository.save(u);

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}

	public Lekar updateLekar(LekarDTO lekarDTO) {
		Lekar lekar = findOne(lekarDTO.getId());

		if (lekar == null) {
			return null;
		}

		lekar.setOcena(lekarDTO.getOcena());
		lekar.setPocetakRadnogVremena(lekarDTO.getPocetakRadnogVremena());
		lekar.setKrajRadnogVremena(lekarDTO.getKrajRadnogVremena());
		lekar.setJedinstveniBrOsiguranika(lekarDTO
				.getJedinstveniBrOsiguranika());

		lekar = save(lekar);

		return lekar;
	}

	public Lekar preracunajOcenuLekara(LekarDTO lekarDTO) {
		Lekar lekar = findOne(lekarDTO.getId());
		float staraOcena = lekar.getOcena();
		float inputOcena = lekarDTO.getOcena();
		float novaOcena = -1;

		if (staraOcena == 0) {
			novaOcena = staraOcena + inputOcena;
		} else {
			novaOcena = (staraOcena + inputOcena) / 2;
		}

		lekar.setOcena(novaOcena);
		lekar = save(lekar);

		return lekar;
	}
	
	/*
	 * Getting all appointments assigned to doctor with given ID
	 */
	public ResponseEntity<List<PregledDTO>> getDoctorAppointments(Long doctorId) {
		Lekar lekar = findOne(doctorId);
		Set<Pregled> pregledi = lekar.getPregledi();
		List<PregledDTO> preglediDTO = new ArrayList<>();
		for (Pregled p : pregledi) {
			preglediDTO.add(new PregledDTO(p));
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}
	
	/*
	 * Sending next appointment/operation request to administrator of clinic
	 */
	public ResponseEntity<ZahtevZaTerminDTO> slanjeZahtevaZaTermin(ZahtevZaTerminDTO zahtevDTO) {
		Date datum = zahtevDTO.getDatum();
		String pocetak = zahtevDTO.getPocetak();
		String kraj = zahtevDTO.getKraj();
		Long tipPregleda = zahtevDTO.getTipPregleda();
		String tipTermina = zahtevDTO.getTipTermina();
		Lekar lekar = zahtevDTO.getLekar();
		MedicinskaSestra sestra = zahtevDTO.getSestra();
		Pacijent pacijent = zahtevDTO.getPacijent();
		
		if (datum == null || pocetak == null || pocetak.equals("") || kraj == null || kraj.equals("")
			|| tipPregleda == -1 || tipTermina == null || tipTermina.equals("") || lekar == null
			|| pacijent == null || sestra == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		ZahtevZaTerminDTO zahtevDTOO = new ZahtevZaTerminDTO(datum, pocetak, kraj, tipPregleda, tipTermina,lekar,sestra,pacijent);
		AdministratorKlinike admin = adminService.findOne((long) 1);

		if (admin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User u = userService.findByJedinstveniBrOsiguranika(admin.getJedinstveniBrOsiguranika());
		admin.setEmail(u.getEmail());
		
		String subject = "Novi zahtev za zakazivanje termina";
		String txt = "Stigao Vam je novi zahtev za zakazivanje termina.";
		try {
			emailService.sendNotification("kcentar4@gmail.com", subject, txt);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ZahtevZaTermin zahtev = new ZahtevZaTermin();
		zahtev.setAdmin(admin);
		zahtev.setDatum(zahtevDTOO.getDatum());
		zahtev.setKraj(zahtevDTOO.getKraj());
		zahtev.setLekar(zahtevDTOO.getLekar());
		zahtev.setSestra(zahtevDTOO.getSestra());
		zahtev.setPacijent(zahtevDTOO.getPacijent());
		zahtev.setPocetak(zahtevDTOO.getPocetak());
		zahtev.setTipPregleda(zahtevDTOO.getTipPregleda());
		zahtev.setTipTermina(zahtevDTOO.getTipTermina());
		
		zahtev = zahtevTerminService.save(zahtev);
		admin = adminService.save(admin);

		return new ResponseEntity<>(new ZahtevZaTerminDTO(zahtev), HttpStatus.OK);
	}

}
