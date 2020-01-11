/***********************************************************************
 * Module:  AdministratorKlinickogCentra.java
 * Author:  Boris
 * Purpose: Defines the Class AdministratorKlinickogCentra
 ***********************************************************************/

package com.isapsw.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class AdministratorKlinickogCentra {

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

	@Column(name = "promenjenaLozinka", nullable = false)
	private Boolean promenjenaLozinka = false;

	@Column(name = "jedinstveniBrOsiguranika", nullable = false)
	private int jedinstveniBrOsiguranika;

	@Transient
	private Set<AdministratorKlinike> administratoriKlinike = new HashSet<AdministratorKlinike>();

	@Transient
	private SifarnikDijagnozaILekova sifarnikDijagnozaILekova;

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String newEmail) {
		email = newEmail;
	}

	public java.lang.String getLozinka() {
		return lozinka;
	}

	public void setLozinka(java.lang.String newLozinka) {
		lozinka = newLozinka;
	}

	public java.lang.String getIme() {
		return ime;
	}

	public void setIme(java.lang.String newIme) {
		ime = newIme;
	}

	public java.lang.String getPrezime() {
		return prezime;
	}

	public void setPrezime(java.lang.String newPrezime) {
		prezime = newPrezime;
	}

	public java.lang.String getAdresa() {
		return adresa;
	}

	public void setAdresa(java.lang.String newAdresa) {
		adresa = newAdresa;
	}

	public java.lang.String getGrad() {
		return grad;
	}

	public void setGrad(java.lang.String newGrad) {
		grad = newGrad;
	}

	public java.lang.String getDrzava() {
		return drzava;
	}

	public void setDrzava(java.lang.String newDrzava) {
		drzava = newDrzava;
	}

	public String getBrTelefona() {
		return brTelefona;
	}

	public void setBrTelefona(String newBrTelefona) {
		brTelefona = newBrTelefona;
	}

	public int getJedinstveniBrOsiguranika() {
		return jedinstveniBrOsiguranika;
	}

	public void setJedinstveniBrOsiguranika(int newJedinstveniBrOsiguranika) {
		jedinstveniBrOsiguranika = newJedinstveniBrOsiguranika;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SifarnikDijagnozaILekova getSifarnikDijagnozaILekova() {
		return sifarnikDijagnozaILekova;
	}

	public void setSifarnikDijagnozaILekova(
			SifarnikDijagnozaILekova sifarnikDijagnozaILekova) {
		this.sifarnikDijagnozaILekova = sifarnikDijagnozaILekova;
	}

	public Boolean getPromenjenaLozinka() {
		return promenjenaLozinka;
	}

	public void setPromenjenaLozinka(Boolean promenjenaLozinka) {
		this.promenjenaLozinka = promenjenaLozinka;
	}

	public Set<AdministratorKlinike> getAdministratoriKlinike() {
		return administratoriKlinike;
	}

	public void setAdministratoriKlinike(
			Set<AdministratorKlinike> administratoriKlinike) {
		this.administratoriKlinike = administratoriKlinike;
	}

}