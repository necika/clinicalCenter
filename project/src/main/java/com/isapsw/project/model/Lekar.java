/***********************************************************************
 * Module:  Lekar.java
 * Author:  Boris
 * Purpose: Defines the Class Lekar
 ***********************************************************************/

package com.isapsw.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Lekar")
public class Lekar {
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

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_doctor", joinColumns = {@JoinColumn(name = "doctor_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "jedinstveniBrOsiguranika")})
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "jedinstveniBrojOsiguranika", nullable = false)
	private int jedinstveniBrOsiguranika;
	
	@Column(name = "ocena", nullable = false)
	private float ocena;
	
	@Transient
	private java.util.Date datumOdlaskaNaGodisnji;
	
	@Transient
	private java.util.Date datumPovratkaSaGodisnjeg;
	
	@Column(name = "pocetakRadnogVremena", nullable = false)
	private String pocetakRadnogVremena;
	
	@Column(name = "krajRadnogVremena", nullable = false)
	private String krajRadnogVremena;
	
	@JsonIgnore
	@OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> pregledi = new HashSet<Pregled>();
	
	
	@Transient
	private Set<IzvestajOPregledu> izvestaji = new HashSet<IzvestajOPregledu>();
	
	@Transient
	private Set<Operacija> operacije = new HashSet<Operacija>();
	
	@JsonIgnore
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private Klinika klinika;

	@Transient
	private SifarnikDijagnozaILekova sifarnikDijagnozaILekova;

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

	public float getOcena() {
		return ocena;
	}

	public void setOcena(float ocena) {
		this.ocena = ocena;
	}

	public java.util.Date getDatumOdlaskaNaGodisnji() {
		return datumOdlaskaNaGodisnji;
	}

	public void setDatumOdlaskaNaGodisnji(java.util.Date datumOdlaskaNaGodisnji) {
		this.datumOdlaskaNaGodisnji = datumOdlaskaNaGodisnji;
	}

	public java.util.Date getDatumPovratkaSaGodisnjeg() {
		return datumPovratkaSaGodisnjeg;
	}

	public void setDatumPovratkaSaGodisnjeg(java.util.Date datumPovratkaSaGodisnjeg) {
		this.datumPovratkaSaGodisnjeg = datumPovratkaSaGodisnjeg;
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

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public Set<IzvestajOPregledu> getIzvestaji() {
		return izvestaji;
	}

	public void setIzvestaji(Set<IzvestajOPregledu> izvestaji) {
		this.izvestaji = izvestaji;
	}

	public Set<Operacija> getOperacije() {
		return operacije;
	}

	public void setOperacije(Set<Operacija> operacije) {
		this.operacije = operacije;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public SifarnikDijagnozaILekova getSifarnikDijagnozaILekova() {
		return sifarnikDijagnozaILekova;
	}

	public void setSifarnikDijagnozaILekova(
			SifarnikDijagnozaILekova sifarnikDijagnozaILekova) {
		this.sifarnikDijagnozaILekova = sifarnikDijagnozaILekova;
	}

	
}