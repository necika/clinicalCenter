package com.isapsw.project.dto;

import com.isapsw.project.model.AdministratorKlinickogCentra;

public class AdministratorKlinickogCentraDTO {

	private Long id;
	private java.lang.String email;
	private java.lang.String lozinka;
	private java.lang.String ime;
	private java.lang.String prezime;
	private java.lang.String adresa;
	private java.lang.String grad;
	private java.lang.String drzava;
	private String brTelefona;
	private Boolean promenjenaLozinka = false;
	private int jedinstveniBrOsiguranika;
	
	public AdministratorKlinickogCentraDTO() {
		super();
	}

	public AdministratorKlinickogCentraDTO(AdministratorKlinickogCentra akc) {
		id = akc.getId();
		email = akc.getEmail();
		lozinka = akc.getLozinka();
		ime = akc.getIme();
		prezime = akc.getPrezime();
		adresa = akc.getAdresa();
		grad = akc.getGrad();
		drzava = akc.getDrzava();
		brTelefona = akc.getBrTelefona();
		promenjenaLozinka = akc.getPromenjenaLozinka();
		setJedinstveniBrOsiguranika(akc.getJedinstveniBrOsiguranika());
	}

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

	public Boolean getPromenjenaLozinka() {
		return promenjenaLozinka;
	}

	public void setPromenjenaLozinka(Boolean promenjenaLozinka) {
		this.promenjenaLozinka = promenjenaLozinka;
	}

	public int getJedinstveniBrOsiguranika() {
		return jedinstveniBrOsiguranika;
	}

	public void setJedinstveniBrOsiguranika(int jedinstveniBrOsiguranika) {
		this.jedinstveniBrOsiguranika = jedinstveniBrOsiguranika;
	}

}
