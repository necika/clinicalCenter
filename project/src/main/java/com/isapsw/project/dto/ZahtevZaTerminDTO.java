package com.isapsw.project.dto;

import java.util.Date;

import com.isapsw.project.model.AdministratorKlinike;
import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Pacijent;
import com.isapsw.project.model.ZahtevZaTermin;

public class ZahtevZaTerminDTO {

	private Long id;
	private Date datum;
	private String pocetak;
	private String kraj;
	private Long tipPregleda;
	private String tipTermina;
	private AdministratorKlinike admin;
	private Lekar lekar;
	private MedicinskaSestra sestra;
	private Pacijent pacijent;
	
	public ZahtevZaTerminDTO() {
		
	}
	
	public ZahtevZaTerminDTO(Date datum, String pocetak, String kraj,
			Long tipPregleda, String tipTermina,Lekar lekar,MedicinskaSestra sestra,Pacijent pacijent) {
		this.datum = datum;
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.tipPregleda = tipPregleda;
		this.tipTermina = tipTermina;
		this.lekar = lekar;
		this.sestra = sestra;
		this.pacijent = pacijent;
	}
	
	public ZahtevZaTerminDTO(ZahtevZaTermin z) {
		this.id = z.getId();
		this.datum = z.getDatum();
		this.pocetak = z.getPocetak();
		this.kraj = z.getKraj();
		this.tipPregleda = z.getTipPregleda();
		this.tipTermina = z.getTipTermina();
		this.admin = z.getAdmin();
		this.lekar = z.getLekar();
		this.sestra = z.getSestra();
		this.pacijent = z.getPacijent();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getPocetak() {
		return pocetak;
	}

	public void setPocetak(String pocetak) {
		this.pocetak = pocetak;
	}

	public String getKraj() {
		return kraj;
	}

	public void setKraj(String kraj) {
		this.kraj = kraj;
	}

	public Long getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(Long tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public String getTipTermina() {
		return tipTermina;
	}

	public void setTipTermina(String tipTermina) {
		this.tipTermina = tipTermina;
	}

	public AdministratorKlinike getAdmin() {
		return admin;
	}

	public void setAdmin(AdministratorKlinike admin) {
		this.admin = admin;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public MedicinskaSestra getSestra() {
		return sestra;
	}

	public void setSestra(MedicinskaSestra sestra) {
		this.sestra = sestra;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}
	
}
