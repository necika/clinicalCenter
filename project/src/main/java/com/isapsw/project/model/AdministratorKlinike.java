/***********************************************************************
 * Module:  AdministratorKlinike.java
 * Author:  Boris
 * Purpose: Defines the Class AdministratorKlinike
 ***********************************************************************/

package com.isapsw.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AdministratorKlinike {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Transient
	private java.lang.String email;

	@Transient
	private java.lang.String lozinka;

	@Transient
	private java.lang.String ime;

	@Transient
	private java.lang.String prezime;

	@Transient
	private java.lang.String adresa;

	@Transient
	private java.lang.String grad;

	@Transient
	private java.lang.String drzava;

	@Transient
	private String brTelefona;

	@Column(name = "jedinstveniBrOsiguranika", nullable = false)
	private int jedinstveniBrOsiguranika;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Klinika klinika;

	@JsonIgnore
	@OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ZahtevZaOdmor> zahteviZaOdmor = new HashSet<ZahtevZaOdmor>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ZahtevZaTermin> zahteviZaTermin = new HashSet<ZahtevZaTermin>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getLozinka() {
		return lozinka;
	}

	public void setLozinka(java.lang.String lozinka) {
		this.lozinka = lozinka;
	}

	public java.lang.String getIme() {
		return ime;
	}

	public void setIme(java.lang.String ime) {
		this.ime = ime;
	}

	public java.lang.String getPrezime() {
		return prezime;
	}

	public void setPrezime(java.lang.String prezime) {
		this.prezime = prezime;
	}

	public java.lang.String getAdresa() {
		return adresa;
	}

	public void setAdresa(java.lang.String adresa) {
		this.adresa = adresa;
	}

	public java.lang.String getGrad() {
		return grad;
	}

	public void setGrad(java.lang.String grad) {
		this.grad = grad;
	}

	public java.lang.String getDrzava() {
		return drzava;
	}

	public void setDrzava(java.lang.String drzava) {
		this.drzava = drzava;
	}

	public String getBrTelefona() {
		return brTelefona;
	}

	public void setBrTelefona(String brTelefona) {
		this.brTelefona = brTelefona;
	}

	public int getJedinstveniBrOsiguranika() {
		return jedinstveniBrOsiguranika;
	}

	public void setJedinstveniBrOsiguranika(int jedinstveniBrOsiguranika) {
		this.jedinstveniBrOsiguranika = jedinstveniBrOsiguranika;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public Set<ZahtevZaOdmor> getZahteviZaOdmor() {
		return zahteviZaOdmor;
	}

	public void setZahteviZaOdmor(Set<ZahtevZaOdmor> zahteviZaOdmor) {
		this.zahteviZaOdmor = zahteviZaOdmor;
	}

	public Set<ZahtevZaTermin> getZahteviZaTermin() {
		return zahteviZaTermin;
	}

	public void setZahteviZaTermin(Set<ZahtevZaTermin> zahteviZaTermin) {
		this.zahteviZaTermin = zahteviZaTermin;
	}
	
}