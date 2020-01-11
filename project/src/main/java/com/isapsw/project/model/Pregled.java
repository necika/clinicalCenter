/***********************************************************************
 * Module:  Pregled.java
 * Author:  Boris
 * Purpose: Defines the Class Pregled
 ***********************************************************************/

package com.isapsw.project.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Model entity representing appointment
 */
@Entity
@Table(name = "Pregled")
public class Pregled {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "datum", nullable = false)
	private java.util.Date datum;

	@Column(name = "pocetak", nullable = false)
	private String vremePocetka;

	@Column(name = "zavrsetak", nullable = false)
	private String vremeZavrsetka;

	@Column(name = "trajanje", nullable = false)
	private String trajanje;

	@Column(name = "cena", nullable = false)
	private java.lang.Double cena;

	@Column(name = "rezervisan", nullable = false)
	private java.lang.Boolean rezervisan = false;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private Sala sala;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private Lekar lekar;
	
	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private Pacijent pacijent;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private MedicinskaSestra medicinskaSestra;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private TipPregleda tipPregleda;

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