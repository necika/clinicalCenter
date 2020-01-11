package com.isapsw.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.isapsw.project.dto.KlinikaDTO;
import com.isapsw.project.dto.LekarDTO;
import com.isapsw.project.dto.SalaDTO;
import com.isapsw.project.model.Klinika;
import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.Sala;
import com.isapsw.project.model.User;
import com.isapsw.project.repository.KlinikaRepository;

@Service
public class KlinikaService {

	@Autowired
	private KlinikaRepository klinikaRepository;
	
	@Autowired
	private UserService userService;

	public Klinika save(Klinika klinika) {
		return klinikaRepository.save(klinika);
	}

	public Klinika findOne(Long id) {
		return klinikaRepository.findById(id).orElseGet(null);
	}
	
	public List<Klinika> findAll() {
        return klinikaRepository.findAll();
    }

    public Page<Klinika> findAll(Pageable page) {
        return klinikaRepository.findAll(page);
    }

    public List<Klinika> findByAdresa(String adresa) {
        return klinikaRepository.findAllByAdresa(adresa);
    }

    public List<Klinika> findByNaziv(String naziv) {
        return klinikaRepository.findAllByNaziv(naziv);
    }

    public List<Klinika> findByOcena(Float ocena) {
        return klinikaRepository.findAllByOcena(ocena);
    }
    
    /*
     * Logic for addition of a new tuple to database table Klinika
     */
    public ResponseEntity<KlinikaDTO> addClinic(KlinikaDTO klinikaDTO) {
    	if (klinikaDTO.getAdresa() == null
                || klinikaDTO.getNaziv() == null
                || klinikaDTO.getOpis() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Klinika klinikaSave = new Klinika();
        klinikaSave.setAdresa(klinikaDTO.getAdresa());
        klinikaSave.setId(klinikaDTO.getId());
        klinikaSave.setNaziv(klinikaDTO.getNaziv());
        klinikaSave.setOcena(klinikaDTO.getOcena());
        klinikaSave.setOpis(klinikaDTO.getOpis());

        klinikaSave = save(klinikaSave);

        return new ResponseEntity<>(new KlinikaDTO(klinikaSave), HttpStatus.CREATED);
    }
    
    /*
	 * Logic for updating of an existing tuple from database table Klinika
	 */
    public ResponseEntity<KlinikaDTO> updateClinic(KlinikaDTO klinikaDTO) {
		Klinika klinika = findOne(klinikaDTO.getId());
		if (klinika == null || klinika.getNaziv().equals("") || klinika.getNaziv() == null
				|| klinika.getOpis().equals("") || klinika.getOpis() == null
				|| klinika.getAdresa().equals("") || klinika.getAdresa() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		klinika.setId(klinikaDTO.getId());
		klinika.setNaziv(klinikaDTO.getNaziv());
		klinika.setAdresa(klinikaDTO.getAdresa());
		klinika.setOpis(klinikaDTO.getOpis());
		klinika.setOcena(klinikaDTO.getOcena());
		klinika = save(klinika);

		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}
    
    /*
     * Search algorithm for browsing of existing tuples in table Klinika
     */
    public List<KlinikaDTO> pretragaKlinike(String pretraga,Page<Klinika> klinike){
    	List<KlinikaDTO> klinikeListPretragaDTO = new ArrayList<KlinikaDTO>();
    	
    	String niz[] = pretraga.split(",");
		String naziv;
		String adresa;
		Float ocena1;
		Float ocena2;
		if(niz[0] == "") {
			naziv = "";
		}else {
			naziv = niz[0];
		}
		if(niz[1] == "") {
			adresa = "";
		}else {
			adresa = niz[1];
		}
		
		try {
			ocena1 = Float.parseFloat(niz[2]);
		}catch(NumberFormatException e) {
			ocena1 = (float) -2;
		}
		
		try {
			ocena2 = Float.parseFloat(niz[3]);
		}catch(NumberFormatException e) {
			ocena2 = (float) -2;
		}
		
		for(Klinika k : klinike) {
			
			Boolean ocenaBol = true;
			Boolean adresaBol = true;
			Boolean nazivBol = true;
			
			if(ocena1 != -2 && ocena2 != -2) {
				if(k.getOcena()>=ocena1 && k.getOcena()<=ocena2){
					ocenaBol = true;
				
				}else {
					ocenaBol = false;
				}
			}else {
				ocenaBol = true;
			}
			
			if(!naziv.equals("")){
				if(k.getNaziv().contains(naziv)){
					nazivBol = true;
					
				}else {
					nazivBol = false;
				}
			}else {
				nazivBol = true;
			
			}
			
			if(!adresa.equals("")){
				
				if(k.getAdresa().contains(adresa)){
					adresaBol = true;
					
				}else {
					adresaBol = false;
				}
			}else {
				adresaBol = true;
			
			}
			
			if(adresaBol && nazivBol && ocenaBol){
				klinikeListPretragaDTO.add(new KlinikaDTO(k));
			}
		}
    	
    	return klinikeListPretragaDTO;
    }

	public Boolean checkKlinikaConstraints(KlinikaDTO klinikaDTO) {
		if (klinikaDTO.getNaziv() == null || klinikaDTO.getAdresa() == null || klinikaDTO.getOpis() == null
				|| klinikaDTO.getOcena() == null) {
			return false;
		}

		return true;
	}

	public Klinika preracunajOcenuKlinike(KlinikaDTO klinikaDTO) {
		Klinika klinika = findOne(klinikaDTO.getId());
		float staraOcena = klinika.getOcena();
		float inputOcena = klinikaDTO.getOcena();
		float novaOcena = -1;
		
		if (staraOcena == 0) {
			novaOcena = staraOcena + inputOcena;
		}
		else {
			novaOcena = (staraOcena + inputOcena) / 2;
		
		}
		klinika.setOcena(novaOcena);
		klinika = save(klinika);

		return klinika;
	}
	
	/*
	 * Getting all the doctors of clinic with given clinic_ID
	 */
	public ResponseEntity<List<LekarDTO>> getClinicDoctors(Long clinicId) {
		Klinika klinika = findOne(clinicId);
		Set<Lekar> lekari = klinika.getLekar();
		List<LekarDTO> lekariDTO = new ArrayList<>();
		for (Lekar l : lekari) {
			User u = userService.findByJedinstveniBrOsiguranika(l
					.getJedinstveniBrOsiguranika());
			l.setIme(u.getIme());
			l.setPrezime(u.getPrezime());
			l.setEmail(u.getEmail());
			l.setBrTelefona(u.getBrTelefona());
			l.setAdresa(u.getAdresa());
			l.setGrad(u.getGrad());
			l.setDrzava(u.getDrzava());
			l.setLozinka(u.getPassword());
			LekarDTO lekarDTO = new LekarDTO(l);
			lekariDTO.add(lekarDTO);
		}
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}
	
	/*
	 * Getting all the ordinations of clinic with given clinic_ID
	 */
	public ResponseEntity<List<SalaDTO>> getClinicOrdinations(Long clinicId) {
		Klinika klinika = findOne(clinicId);
		Set<Sala> sale = klinika.getSale();
		List<SalaDTO> saleDTO = new ArrayList<>();
		for (Sala s : sale) {
			saleDTO.add(new SalaDTO(s));
		}
		return new ResponseEntity<>(saleDTO, HttpStatus.OK);
	}
}
