package com.isapsw.project.dto;

import com.isapsw.project.model.Pacijent;
import com.isapsw.project.model.ZdravstveniKarton;

public class PacijentDTO {

	private Long id;

	private java.lang.String email;

	private java.lang.String lozinka;

	private java.lang.String ime;

	private java.lang.String prezime;

	private java.lang.String adresa;

	private java.lang.String grad;

	private java.lang.String drzava;

	private String brTelefona;

	private int jedinstveniBrOsiguranika;
	
	private ZdravstveniKarton zdravstveniKarton;

	public PacijentDTO() {
		super();
	}

	public PacijentDTO(Pacijent pac) {
		email = pac.getEmail();
		lozinka = pac.getLozinka();
		ime = pac.getIme();
		prezime = pac.getPrezime();
		adresa = pac.getAdresa();
		grad = pac.getGrad();
		drzava = pac.getDrzava();
		brTelefona = pac.getBrTelefona();
		jedinstveniBrOsiguranika = pac.getJedinstveniBrOsiguranika();
		id = pac.getId();
		this.zdravstveniKarton = pac.getZdravstveniKarton();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKarton zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
	}
	
	
}