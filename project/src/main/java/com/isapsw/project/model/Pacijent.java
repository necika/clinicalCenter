/***********************************************************************
 * Module:  Pacijent.java
 * Author:  Boris
 * Purpose: Defines the Class Pacijent
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Pacijent")
public class Pacijent {

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
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_patient", joinColumns = {@JoinColumn(name = "patient_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "jedinstveniBrOsiguranika")})
	private User user;
	
	@OneToOne
	private ZdravstveniKarton zdravstveniKarton;
	
	@Transient
	private java.util.List<Klinika> klinika;

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

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKarton zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
	}

	public java.util.List<Klinika> getKlinika() {
		return klinika;
	}

	public void setKlinika(java.util.List<Klinika> klinika) {
		this.klinika = klinika;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}