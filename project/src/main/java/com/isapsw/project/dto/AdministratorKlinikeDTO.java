package com.isapsw.project.dto;

import java.util.HashSet;
import java.util.Set;

import com.isapsw.project.model.AdministratorKlinike;
import com.isapsw.project.model.Klinika;
import com.isapsw.project.model.ZahtevZaOdmor;

public class AdministratorKlinikeDTO {

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
	private Klinika klinika;
	private Set<ZahtevZaOdmor> zahteviZaOdmor = new HashSet<ZahtevZaOdmor>();

	public AdministratorKlinikeDTO() {
		super();
	}

	public AdministratorKlinikeDTO(AdministratorKlinike ak) {
		email = ak.getEmail();
		lozinka = ak.getLozinka();
		ime = ak.getIme();
		prezime = ak.getPrezime();
		adresa = ak.getAdresa();
		grad = ak.getGrad();
		drzava = ak.getDrzava();
		brTelefona = ak.getBrTelefona();
		id = ak.getId();
		jedinstveniBrOsiguranika = ak.getJedinstveniBrOsiguranika();
		klinika = ak.getKlinika();
		zahteviZaOdmor = ak.getZahteviZaOdmor();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<ZahtevZaOdmor> getZahteviZaOdmor() {
		return zahteviZaOdmor;
	}

	public void setZahteviZaOdmor(Set<ZahtevZaOdmor> zahteviZaOdmor) {
		this.zahteviZaOdmor = zahteviZaOdmor;
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
}
