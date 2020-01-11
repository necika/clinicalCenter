package com.isapsw.project.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.isapsw.project.model.IzvestajOPregledu;
import com.isapsw.project.model.Klinika;
import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Recept;
import com.isapsw.project.model.User;

public class MedicinskaSestraDTO {

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
	private Boolean odobrenGodisnji = false;
	private Date datumOdlaskaNaGodisnji = null;
	private Date datumPovratkaSaGodisnjeg = null;
	private String pocetakRadnogVremena;
	private String krajRadnogVremena;
	private Klinika klinika;
	private User user;
	private Set<Recept> recepti = new HashSet<Recept>();
	
	public MedicinskaSestraDTO() {
		super();
	}
	
	public MedicinskaSestraDTO(MedicinskaSestra l) {
		id = l.getId();
		email = l.getEmail();
		lozinka = l.getLozinka();
		ime = l.getIme();
		prezime = l.getPrezime();
		adresa = l.getAdresa();
		grad = l.getGrad();
		drzava = l.getDrzava();
		brTelefona = l.getBrTelefona();
		jedinstveniBrOsiguranika = l.getJedinstveniBrOsiguranika();
		odobrenGodisnji = l.getOdobrenGodisnji();
		datumOdlaskaNaGodisnji = l.getDatumOdlaskaNaGodisnji();
		datumPovratkaSaGodisnjeg = l.getDatumPovratkaSaGodisnjeg();
		pocetakRadnogVremena = l.getPocetakRadnogVremena();
		krajRadnogVremena = l.getKrajRadnogVremena();
		klinika = l.getKlinika();
		user = l.getUser();
		this.recepti = l.getRecepti();
	}
	public MedicinskaSestraDTO(User l) {
		id = l.getId();
		email = l.getEmail();
		lozinka = l.getPassword();
		ime = l.getIme();
		prezime = l.getPrezime();
		grad = l.getGrad();
		drzava = l.getDrzava();
		brTelefona = l.getBrTelefona();
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

	public int getJedinstveniBrOsiguranika() {
		return jedinstveniBrOsiguranika;
	}

	public void setJedinstveniBrOsiguranika(int jedinstveniBrOsiguranika) {
		this.jedinstveniBrOsiguranika = jedinstveniBrOsiguranika;
	}

	public Date getDatumOdlaskaNaGodisnji() {
		return datumOdlaskaNaGodisnji;
	}

	public void setDatumOdlaskaNaGodisnji(Date datumOdlaskaNaGodisnji) {
		this.datumOdlaskaNaGodisnji = datumOdlaskaNaGodisnji;
	}

	public Date getDatumPovratkaSaGodisnjeg() {
		return datumPovratkaSaGodisnjeg;
	}

	public void setDatumPovratkaSaGodisnjeg(Date datumPovratkaSaGodisnjeg) {
		this.datumPovratkaSaGodisnjeg = datumPovratkaSaGodisnjeg;
	}
	public Boolean getOdobrenGodisnji() {
		return odobrenGodisnji;
	}
	public void setOdobrenGodisnji(Boolean odobrenGodisnji) {
		this.odobrenGodisnji = odobrenGodisnji;
	}
	public String getPocetakRadnogVremena() {
		return pocetakRadnogVremena;
	}
	public void setPocetakRadnogVremena(String pocetakRadnogVremena) {
		this.pocetakRadnogVremena = pocetakRadnogVremena;
	}
	public String getKrajRadnogVremena() {
		return krajRadnogVremena;
	}
	public void setKrajRadnogVremena(String krajRadnogVremena) {
		this.krajRadnogVremena = krajRadnogVremena;
	}
	public Klinika getKlinika() {
		return klinika;
	}
	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public Set<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Recept> recepti) {
		this.recepti = recepti;
	}

	
	
}
