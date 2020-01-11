package com.isapsw.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.isapsw.project.dto.IzvestajOPregleduDTO;
import com.isapsw.project.dto.ZdravstveniKartonDTO;
import com.isapsw.project.model.IzvestajOPregledu;
import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Recept;
import com.isapsw.project.model.SifarnikDijagnozaILekova;
import com.isapsw.project.model.ZdravstveniKarton;
import com.isapsw.project.repository.ZdravstveniKartonRepository;

@Service
public class ZdravstveniKartonService {

	@Autowired
	private ZdravstveniKartonRepository kartonRepository;

	@Autowired
	private IzvestajOPregleduService izvestajService;
	
	@Autowired
	private ReceptService receptService;
	
	@Autowired
	private MedicinskaSestraService sestraService;
	
	
	@Autowired
	private SifarnikDijagnozaILekovaService sifarnikService;
	
	@Autowired
	private PacijentService pacijentService;

	public ZdravstveniKarton findOne(Long id) {
		return kartonRepository.findById(id).orElseGet(null);
	}

	public ZdravstveniKarton save(ZdravstveniKarton karton) {
		return kartonRepository.save(karton);
	}

	public List<ZdravstveniKarton> findAll() {
		return kartonRepository.findAll();
	}

	public ResponseEntity<ZdravstveniKartonDTO> getZdravstveniKartonPacijentaService(Long id) {
		ZdravstveniKarton karton = findOne(id);

		if (karton == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ZdravstveniKartonDTO(karton), HttpStatus.CREATED);
	}

	public ResponseEntity<ZdravstveniKartonDTO> izmenaZdravstvenogKartona(String osnovniPodaciID) {
		String[] niz = osnovniPodaciID.split(",");
		String osnovniPodaci = niz[0];
		Long id = Long.parseLong(niz[1]);
		ZdravstveniKarton karton = findOne(id);

		if (karton == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		karton.setOsnovniPodaci(osnovniPodaci);
		karton = save(karton);

		return new ResponseEntity<>(new ZdravstveniKartonDTO(karton), HttpStatus.CREATED);
	}
	//uraditi sad da medicinska sestra prisustvuje pregledu

	public void addIzvestaj(String parametri) {
		String[] niz = parametri.split(",");
		Long idKartona = Long.parseLong(niz[0]);
		ZdravstveniKarton karton = findOne(idKartona);
		String podaci = niz[1];
		Long idSestre = Long.parseLong(niz[3]);
		MedicinskaSestra sestra = sestraService.findOne(idSestre);
		IzvestajOPregleduDTO izvestajDTO = new IzvestajOPregleduDTO(karton, podaci);
		IzvestajOPregledu izvestaj = new IzvestajOPregledu();
		izvestaj.setPodaci(izvestajDTO.getPodaci());
		izvestaj.setZdravstveniKarton(izvestajDTO.getZdravstveniKarton());
		izvestaj = izvestajService.save(izvestaj);
		if(niz[2].contains("_")) {
			String[] niz3 = niz[2].split("_");
			for(String p : niz3) {
				String[] niz2 = p.split("-");
				SifarnikDijagnozaILekova sifarnik = sifarnikService.findByLekovi(niz2[1]);
				sifarnik.setLekovi(niz2[1]);
				sifarnik.setDijagnoze(niz2[0]);
				Recept recept = new Recept();
				recept.setNazivRecepta(sifarnik.getDijagnoze());
				recept.setOveren(false);
				recept.setSifarnik(sifarnik);
				recept.setIzvestajOPregledu(izvestaj);
				recept.setSestra(sestra);
				recept = receptService.save(recept);
			}
			
		}else {
			String[] niz1 = niz[2].split("-");
			SifarnikDijagnozaILekova sifarnik = sifarnikService.findByLekovi(niz[1]);
			sifarnik.setLekovi(niz1[1]);
			sifarnik.setDijagnoze(niz1[0]);
			Recept recept = new Recept();
			recept.setNazivRecepta(sifarnik.getDijagnoze());
			recept.setOveren(false);
			recept.setSifarnik(sifarnik);
			recept.setIzvestajOPregledu(izvestaj);
			recept.setSestra(sestra);
			recept = receptService.save(recept);
		}
		
	}
}
