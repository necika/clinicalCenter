package com.isapsw.project.dto;

import java.util.Date;

import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Pacijent;
import com.isapsw.project.model.Pregled;
import com.isapsw.project.model.Sala;
import com.isapsw.project.model.TipPregleda;

/*
 * Data Transfer Object class for entity Pregled
 * (POJO Java class with mandatory properties and get/set methods for given entity)
 */
public class PregledDTO {

	private Long id;
	private java.util.Date datum;
	private String vremePocetka;
	private String vremeZavrsetka;
	private String trajanje;
	private java.lang.Double cena;
	private java.lang.Boolean rezervisan = false;
	private Sala sala;
	private Pacijent pacijent;
	private Lekar lekar;
	private MedicinskaSestra medicinskaSestra;
	private TipPregleda tipPregleda;

	public PregledDTO() {
		
	}

	public PregledDTO(Date datum, String vremePocetka, String vremeZavrsetka,
			String trajanje, Double cena, Boolean rezervisan, Sala sala,
			Lekar lekar, MedicinskaSestra medicinskaSestra,
			TipPregleda tipPregleda,Pacijent pacijent,MedicinskaSestra sestra) {
		super();
		this.datum = datum;
		this.vremePocetka = vremePocetka;
		this.vremeZavrsetka = vremeZavrsetka;
		this.trajanje = trajanje;
		this.cena = cena;
		this.rezervisan = rezervisan;
		this.sala = sala;
		this.lekar = lekar;
		this.medicinskaSestra = medicinskaSestra;
		this.tipPregleda = tipPregleda;
		this.pacijent = pacijent;
		this.medicinskaSestra = sestra; 
	}
	
	public PregledDTO(Pregled p) {
		this.setId(p.getId());
		this.datum = p.getDatum();
		this.vremePocetka = p.getVremePocetka();
		this.vremeZavrsetka = p.getVremeZavrsetka();
		this.trajanje = p.getTrajanje();
		this.cena = p.getCena();
		this.rezervisan = p.getRezervisan();
		this.sala = p.getSala();
		this.lekar = p.getLekar();
		this.medicinskaSestra = p.getMedicinskaSestra();
		this.tipPregleda = p.getTipPregleda();
		this.pacijent = p.getPacijent();
		this.medicinskaSestra = p.getMedicinskaSestra();
	}

	public java.util.Date getDatum() {
		return datum;
	}

	public void setDatum(java.util.Date datum) {
		this.datum = datum;
	}

	public String getVremePocetka() {
		return vremePocetka;
	}

	public void setVremePocetka(String vremePocetka) {
		this.vremePocetka = vremePocetka;
	}

	public String getVremeZavrsetka() {
		return vremeZavrsetka;
	}

	public void setVremeZavrsetka(String vremeZavrsetka) {
		this.vremeZavrsetka = vremeZavrsetka;
	}

	public String getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(String trajanje) {
		this.trajanje = trajanje;
	}

	public java.lang.Double getCena() {
		return cena;
	}

	public void setCena(java.lang.Double cena) {
		this.cena = cena;
	}

	public java.lang.Boolean getRezervisan() {
		return rezervisan;
	}

	public void setRezervisan(java.lang.Boolean rezervisan) {
		this.rezervisan = rezervisan;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public MedicinskaSestra getMedicinskaSestra() {
		return medicinskaSestra;
	}

	public void setMedicinskaSestra(MedicinskaSestra medicinskaSestra) {
		this.medicinskaSestra = medicinskaSestra;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}
	
	
}
