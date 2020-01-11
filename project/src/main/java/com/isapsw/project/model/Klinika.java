/***********************************************************************
 * Module:  Klinika.java
 * Author:  Boris
 * Purpose: Defines the Class Klinika
 ***********************************************************************/

package com.isapsw.project.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Klinika")
public class Klinika {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	@Column(name = "adresa", nullable = false)
	private String adresa;
	
	@Column(name = "opis")
	private String opis;
	
	@Transient
	private int slobodniTermini;
	
	@Transient
	private ArrayList<String> cenovnik = new ArrayList<String>();
	
	@Column(name = "ocena",nullable = false)
	private Float ocena;
	
	@Transient
	private Set<Pacijent> pacijent;
	
	@Transient
	private Set<TipPregleda> tipPregleda;

	@JsonIgnore
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Lekar> lekar = new HashSet<Lekar>();
	
	@Transient
	private Set<IzvestajOPoslovanju> izvestajOPoslovanju;
	
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Sala> sale = new HashSet<Sala>();
	
	@Transient
	private Set<AdministratorKlinike> administratorKlinike = new HashSet<AdministratorKlinike>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getSlobodniTermini() {
		return slobodniTermini;
	}

	public void setSlobodniTermini(int slobodniTermini) {
		this.slobodniTermini = slobodniTermini;
	}

	public ArrayList<String> getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(ArrayList<String> cenovnik) {
		this.cenovnik = cenovnik;
	}

	public Float getOcena() {
		return ocena;
	}

	public void setOcena(Float ocena) {
		this.ocena = ocena;
	}

	public Set<Pacijent> getPacijent() {
		return pacijent;
	}

	public void setPacijent(Set<Pacijent> pacijent) {
		this.pacijent = pacijent;
	}

	public Set<TipPregleda> getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(Set<TipPregleda> tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Set<Lekar> getLekar() {
		return lekar;
	}

	public void setLekar(Set<Lekar> lekar) {
		this.lekar = lekar;
	}

	public Set<IzvestajOPoslovanju> getIzvestajOPoslovanju() {
		return izvestajOPoslovanju;
	}

	public void setIzvestajOPoslovanju(Set<IzvestajOPoslovanju> izvestajOPoslovanju) {
		this.izvestajOPoslovanju = izvestajOPoslovanju;
	}

	public Set<Sala> getSale() {
		return sale;
	}

	public void setSale(Set<Sala> sale) {
		this.sale = sale;
	}

	public Set<AdministratorKlinike> getAdministratorKlinike() {
		return administratorKlinike;
	}

	public void setAdministratorKlinike(
			Set<AdministratorKlinike> administratorKlinike) {
		this.administratorKlinike = administratorKlinike;
	}
	
}