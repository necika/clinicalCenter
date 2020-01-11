package com.isapsw.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isapsw.project.dto.AdministratorKlinikeDTO;
import com.isapsw.project.dto.ZahtevZaOdmorDTO;
import com.isapsw.project.dto.ZahtevZaTerminDTO;
import com.isapsw.project.model.AdministratorKlinike;
import com.isapsw.project.model.Authority;
import com.isapsw.project.model.Klinika;
import com.isapsw.project.model.User;
import com.isapsw.project.model.ZahtevZaOdmor;
import com.isapsw.project.model.ZahtevZaTermin;
import com.isapsw.project.repository.AdministratorKlinikeRepository;
import com.isapsw.project.repository.UserRepository;

@Service
public class AdministratorKlinikeService {

	@Autowired
	private AdministratorKlinikeRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ZahtevZaOdmorService zahtevService;
	
	@Autowired
	private ZahtevZaTerminService zahtevZaTerminService;

	public AdministratorKlinike save(AdministratorKlinike admin) {
		return adminRepository.save(admin);
	}

	public AdministratorKlinike findByJedinstveniBrOsiguranika(int broj) {
		return adminRepository.findByJedinstveniBrOsiguranika(broj);
	}
	
	public AdministratorKlinike findOne(Long id) {
		return adminRepository.findById(id).orElseGet(null);
	}

	public List<AdministratorKlinike> findAll() {
		return adminRepository.findAll();
	}

	public Page<AdministratorKlinike> findAll(Pageable page) {
		return adminRepository.findAll(page);
	}
	
	/*
	 * Request to database for getting available tuples from database table AdministratorKlinike
	 */
	public ResponseEntity<List<AdministratorKlinikeDTO>> getAvailableAdmins() {
		List<AdministratorKlinike> administratorKlinike = findAll();
		List<AdministratorKlinikeDTO> administratorKlinikeDTO = new ArrayList<>();

		for (AdministratorKlinike a : administratorKlinike) {
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
			administratorKlinikeDTO.add(new AdministratorKlinikeDTO(a));
		}

		return new ResponseEntity<>(administratorKlinikeDTO, HttpStatus.OK);
	}
	
	/*
	 * Request to database for getting tuple by given ID from database table AdministratorKlinike
	 */
	public ResponseEntity<AdministratorKlinikeDTO> getAdminById(Long id) {
		AdministratorKlinike adminKlinike = findOne(id);
		if (adminKlinike == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		User u = userService.findByJedinstveniBrOsiguranika(adminKlinike.getJedinstveniBrOsiguranika());
		adminKlinike.setIme(u.getIme());
		adminKlinike.setPrezime(u.getPrezime());
		adminKlinike.setEmail(u.getEmail());
		adminKlinike.setBrTelefona(u.getBrTelefona());
		adminKlinike.setAdresa(u.getAdresa());
		adminKlinike.setGrad(u.getGrad());
		adminKlinike.setDrzava(u.getDrzava());
		adminKlinike.setLozinka(u.getPassword());
		
		return new ResponseEntity<>(new AdministratorKlinikeDTO(adminKlinike), HttpStatus.OK);
	}
	
	/*
	 * Logic for addition of a new tuple to database table AdministratorKlinike
	 */
	public ResponseEntity<AdministratorKlinikeDTO> addAdmin(AdministratorKlinikeDTO adminDTO, String klinika) {
		if (adminDTO.getBrTelefona() == null || adminDTO.getAdresa() == null
				|| adminDTO.getDrzava() == null || adminDTO.getEmail() == null
				|| adminDTO.getGrad() == null || adminDTO.getIme() == null
				|| adminDTO.getLozinka() == null
				|| adminDTO.getPrezime() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Long odabranaKlinika = (long) -1;
		try {
			odabranaKlinika = Long.parseLong(klinika);
		}
		catch (Exception e) {
			odabranaKlinika = (long) -1;
		}

		AdministratorKlinike adminSave = new AdministratorKlinike();
		Klinika kl = klinikaService.findOne(odabranaKlinika);
		if (kl == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		adminSave.setKlinika(kl);
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

		return new ResponseEntity<>(new AdministratorKlinikeDTO(adminSave),
				HttpStatus.CREATED);
	}
	
	/*
	 * Logic for updating of an existing tuple from database table AdministratorKlinike
	 */
	public ResponseEntity<AdministratorKlinikeDTO> updateAdmin(AdministratorKlinikeDTO adminDTO) {
		if (adminDTO.getBrTelefona() == null || adminDTO.getAdresa() == null
				|| adminDTO.getDrzava() == null || adminDTO.getEmail() == null
				|| adminDTO.getGrad() == null || adminDTO.getIme() == null
				|| adminDTO.getLozinka() == null
				|| adminDTO.getPrezime() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		AdministratorKlinike admin = findOne(adminDTO.getId());
		admin.setKlinika(adminDTO.getKlinika());
		admin.setJedinstveniBrOsiguranika(adminDTO.getJedinstveniBrOsiguranika());
		
		User u = userService.findByJedinstveniBrOsiguranika(admin.getJedinstveniBrOsiguranika());
		u.setUsername(adminDTO.getEmail());
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
		admin = save(admin);
		
		return new ResponseEntity<>(new AdministratorKlinikeDTO(admin), HttpStatus.OK);
  }
  
	public Set<AdministratorKlinike> pronadjiPoIdKlinike(Long id) {
		return adminRepository.pronadjiPoIdKlinike(id);
	}

	/*
	 * Password updating for given administrator of clinic
	 */
	public ResponseEntity<AdministratorKlinikeDTO> changePassword(String lozinka) {
		Long id;
		String novaLozinka;

		String queryParameters[] = lozinka.split(",");
		try {
			id = Long.parseLong(queryParameters[0]);
		} catch (Exception e) {
			id = (long) -1;
		}

		novaLozinka = queryParameters[1];

		AdministratorKlinike admin = findOne(id);
		if (admin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(novaLozinka);
		
		admin.setLozinka(encodedPassword);
		admin = save(admin);

		return new ResponseEntity<>(new AdministratorKlinikeDTO(admin), HttpStatus.OK);
	}
	
	/*
	 * Getting all the vacation/time-off requests sent to administrator with given ID
	 */
	public ResponseEntity<List<ZahtevZaOdmorDTO>> getVacationRequests(Long adminId) {
		AdministratorKlinike admin = findOne(adminId);
		Set<ZahtevZaOdmor> zahtevi = admin.getZahteviZaOdmor();
		List<ZahtevZaOdmorDTO> zahteviDTO = new ArrayList<>();
		for (ZahtevZaOdmor z : zahtevi) {
			zahteviDTO.add(new ZahtevZaOdmorDTO(z));
		}
		return new ResponseEntity<>(zahteviDTO, HttpStatus.OK);
	}
	
	/*
	 * Sending response to user's vacation/time-off request
	 */
	public ResponseEntity<ZahtevZaOdmorDTO> sendVacationResponse(ZahtevZaOdmorDTO zahtevDTO, String odgovor) {
		String subject;
		String txt;
		String flag;
		String parts[] = odgovor.split("!");
		
		subject = parts[0];
		txt = parts[1];
		flag = parts[2];
		
		ZahtevZaOdmor zahtev = zahtevService.findOne(zahtevDTO.getId());
		try {
			emailService.sendNotification(zahtev.getEmail(), subject, txt);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		zahtev = zahtevService.save(zahtev);
		
		return new ResponseEntity<>(new ZahtevZaOdmorDTO(zahtev), HttpStatus.CREATED);
	}

	public ResponseEntity<List<ZahtevZaTerminDTO>> sveTerminiZaPregledeService() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User u = userService.findByUsername(authentication.getName());
		if(u == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		AdministratorKlinike admin = findByJedinstveniBrOsiguranika(u.getJedinstveniBrOsiguranika());
		if(admin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		List<ZahtevZaTerminDTO> zahteviDTO = new ArrayList<>();
		List<ZahtevZaTermin> zahtevi = zahtevZaTerminService.findAll();
		if(zahtevi == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		for(ZahtevZaTermin z : zahtevi) {
			if(z.getAdmin().getId() == admin.getId()) {
				zahteviDTO.add(new ZahtevZaTerminDTO(z));
			}
		}
		
		
		return new ResponseEntity<>(zahteviDTO, HttpStatus.OK);
	}

}
