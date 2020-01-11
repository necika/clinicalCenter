package com.isapsw.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.isapsw.project.dto.LekarDTO;
import com.isapsw.project.dto.PregledDTO;
import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Pacijent;
import com.isapsw.project.model.Pregled;
import com.isapsw.project.model.Sala;
import com.isapsw.project.model.TipPregleda;
import com.isapsw.project.model.User;
import com.isapsw.project.repository.PregledRepository;

@Service
public class PregledService {
	
	@Autowired
	private PregledRepository pregledRepository;
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private MedicinskaSestraService sestraService;
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private TipPregledaService tipService;
	
	@Autowired
	private UserService userService;
	
	public Pregled findOne(Long id) {
		return pregledRepository.findById(id).orElseGet(null);
	}

	public List<Pregled> findAll() {
		return pregledRepository.findAll();
	}

	public Page<Pregled> findAll(Pageable page) {
		return pregledRepository.findAll(page);
	}

	public Pregled save(Pregled pregled) {
		return pregledRepository.save(pregled);
	}

	public void remove(Long id) {
		pregledRepository.deleteById(id);
	}

	public List<PregledDTO> findAllByRezervisan(Boolean rezervisan) {
		List<Pregled> pregledi = pregledRepository.findAllByRezervisan(rezervisan);
		List<PregledDTO> preglediDTO = new ArrayList<>();

		for (Pregled p : pregledi) {
			preglediDTO.add(new PregledDTO(p));
		}
		return preglediDTO;
	}
	
	/*
	 * Getting all available tuples from database table Pregled
	 */
	public ResponseEntity<List<PregledDTO>> getAvailableAppointments() {
		List<Pregled> pregledi = findAll();
		List<PregledDTO> preglediDTO = new ArrayList<>();
		for (Pregled p : pregledi) {
			preglediDTO.add(new PregledDTO(p));
		}

		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}
	
	/*
	 * Sending request to database table Pregled for tuple specified by given ID
	 */
	public ResponseEntity<PregledDTO> getAppointmentById(Long id) {
		Pregled pregled = findOne(id);
		if (pregled == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		User u = userService.findByJedinstveniBrOsiguranika(pregled.getLekar().getJedinstveniBrOsiguranika());
		pregled.getLekar().setIme(u.getIme());
		pregled.getLekar().setPrezime(u.getPrezime());
		pregled.getLekar().setAdresa(u.getAdresa());
		pregled.getLekar().setGrad(u.getGrad());
		pregled.getLekar().setDrzava(u.getDrzava());
		pregled.getLekar().setBrTelefona(u.getBrTelefona());
		pregled.getLekar().setEmail(u.getEmail());	
		
		User uu = userService.findByJedinstveniBrOsiguranika(pregled.getPacijent().getJedinstveniBrOsiguranika());
		pregled.getPacijent().setIme(uu.getIme());
		pregled.getPacijent().setPrezime(uu.getPrezime());
		pregled.getPacijent().setAdresa(uu.getAdresa());
		pregled.getPacijent().setGrad(uu.getGrad());
		pregled.getPacijent().setDrzava(uu.getDrzava());
		pregled.getPacijent().setBrTelefona(uu.getBrTelefona());
		pregled.getPacijent().setEmail(uu.getEmail());
		pregled.getPacijent().setJedinstveniBrOsiguranika(uu.getJedinstveniBrOsiguranika());
		
		User uuu = userService.findByJedinstveniBrOsiguranika(pregled.getMedicinskaSestra().getJedinstveniBrOsiguranika());
		pregled.getMedicinskaSestra().setIme(uuu.getIme());
		pregled.getMedicinskaSestra().setPrezime(uuu.getPrezime());
		pregled.getMedicinskaSestra().setAdresa(uuu.getAdresa());
		pregled.getMedicinskaSestra().setGrad(uuu.getGrad());
		pregled.getMedicinskaSestra().setDrzava(uuu.getDrzava());
		pregled.getMedicinskaSestra().setBrTelefona(uuu.getBrTelefona());
		pregled.getMedicinskaSestra().setEmail(uuu.getEmail());
		pregled.getMedicinskaSestra().setJedinstveniBrOsiguranika(uuu.getJedinstveniBrOsiguranika());
		
		
		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}
	
	/*
	 * Logic for addition of a new tuple to database table Pregled
	 */
	public ResponseEntity<PregledDTO> addPregled(String entiteti, PregledDTO pregledDTO) {
		System.out.println(entiteti);
		Long odabranaSala = (long) -1;
		Long odabraniLekar = (long) -1;
		Long odabraniTip = (long) -1;
		Long odabranaSestra = (long) -1;
		String entitiesQuery[] = entiteti.split(",");
		
		try {
			odabranaSala = Long.parseLong(entitiesQuery[0]);
		}
		catch (Exception e) {
			odabranaSala = (long) -1;
		}
		
		try {
			odabraniLekar = Long.parseLong(entitiesQuery[1]);
		}
		catch (Exception e) {
			odabraniLekar = (long) -1;
		}
		
		try {
			odabraniTip = Long.parseLong(entitiesQuery[2]);
		}
		catch (Exception e) {
			odabraniTip = (long) -1;
		}
		
		try {
			odabranaSestra = Long.parseLong(entitiesQuery[3]);
		}
		catch (Exception e) {
			odabranaSestra = (long) -1;
		}
		
		int pocetakSat = Integer.parseInt(pregledDTO.getVremePocetka().split(":")[0]);
		int pocetakMinut = Integer.parseInt(pregledDTO.getVremePocetka().split(":")[1]);

		int krajSat = Integer.parseInt(pregledDTO.getVremeZavrsetka().split(":")[0]);
		int krajMinut = Integer.parseInt(pregledDTO.getVremeZavrsetka().split(":")[1]);

		if (pocetakSat < 0 || pocetakSat >= 25 || krajSat < 0 || krajSat >= 25 || pocetakMinut < 0
				|| pocetakMinut >= 60 || krajMinut < 0 || krajMinut >= 60 || pocetakSat > krajSat
				|| (pocetakSat == krajSat && pocetakMinut > krajMinut)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Pregled pregled = new Pregled();
		pregled.setDatum(pregledDTO.getDatum());
		pregled.setVremePocetka(pregledDTO.getVremePocetka());
		pregled.setVremeZavrsetka(pregledDTO.getVremeZavrsetka());
		pregled.setTrajanje(pregledDTO.getTrajanje());
		pregled.setCena(pregledDTO.getCena());
		
		if (salaService.findOne(odabranaSala) == null || lekarService.findOne(odabraniLekar) == null
				|| tipService.findOne(odabraniTip) == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Lekar lekar = lekarService.findOne(odabraniLekar);
		Sala sala = salaService.findOne(odabranaSala);
		MedicinskaSestra sestra = sestraService.findOne(odabranaSestra);
		
		pregled.setSala(sala);
		pregled.setLekar(lekar);
		pregled.setMedicinskaSestra(sestra);
		pregled.setTipPregleda(tipService.findOne(odabraniTip));
		
		lekar = lekarService.save(lekar);
		sala = salaService.save(sala);
		sestra = sestraService.save(sestra);
		pregled = save(pregled);
		
		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.CREATED);
	}

	//name je email ulogovanog korisnika,id je id datog pregleda
	public List<PregledDTO> zakaziPredefinisaniPregledService(String name, Long id) {
		User u = userService.findByUsername(name);
		Pacijent pac = pacijentService.findByJedinstveniBrOsiguranika(u.getJedinstveniBrOsiguranika());//pacijent
		Pregled pregled = findOne(id);
		pregled.setRezervisan(true);
		pregled.setPacijent(pac);
		pregled = save(pregled);
				
		return null;
	}

	//baca ovo detached entity passed to persist:
	public ResponseEntity<PregledDTO> addDodatniPregled(PregledDTO pregledDTO, String entiteti) {
		String[] niz = entiteti.split(",");
		String datum = niz[0];
		Long idSale = Long.parseLong(niz[1]);
		Sala sala = salaService.findOne(idSale);
		Long idZahteva = Long.parseLong(niz[2]);
		Long idTipa = Long.parseLong(niz[3]);
		TipPregleda tip = tipService.findOne(idTipa);
		Lekar lekar = lekarService.findOne(Long.parseLong(niz[4]));
		String pattern = "MM-dd-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = simpleDateFormat.parse(datum);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pregled pregled = new Pregled();
		pregled.setCena(pregledDTO.getCena());
		pregled.setDatum(date);
		pregled.setLekar(lekar);
		pregled.setMedicinskaSestra(pregledDTO.getMedicinskaSestra());
		pregled.setPacijent(pregledDTO.getPacijent());
		pregled.setRezervisan(false);
		pregled.setSala(sala);
		pregled.setTipPregleda(tip);
		pregled.setTrajanje(pregledDTO.getTrajanje());
		pregled.setVremePocetka(pregledDTO.getVremePocetka());
		pregled.setVremeZavrsetka(pregledDTO.getVremeZavrsetka());
		pregled = save(pregled);
		
		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.CREATED);
	}
	
}
