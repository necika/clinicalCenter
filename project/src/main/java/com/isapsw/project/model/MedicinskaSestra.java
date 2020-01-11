
package com.isapsw.project.model;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MedicinskaSestra")
public class MedicinskaSestra {

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
	
	@Column(name = "odobrenGodisnji", nullable = false)
	private Boolean odobrenGodisnji = false;
	
	@Column(name = "datumOdlaskaNaGodisnji", nullable = true)
	private Date datumOdlaskaNaGodisnji = null;
	
	@Column(name = "datumPovratkaSaGodisnjeg", nullable = true)
	private Date datumPovratkaSaGodisnjeg = null;
	
	@Column(name = "pocetakRadnogVremena", nullable = false)
	private String pocetakRadnogVremena;
	
	@Column(name = "krajRadnogVremena", nullable = false)
	private String krajRadnogVremena;

	@JsonIgnore
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private Klinika klinika;
	
	@JsonIgnore
	@OneToMany(mappedBy = "sestra", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Recept> recepti = new HashSet<Recept>();
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_nurse", joinColumns = {@JoinColumn(name = "medicinska_sestra_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "jedinstveniBrOsiguranika")})
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "medicinskaSestra", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> pregledi = new HashSet<Pregled>();
	
	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}
	

	public Boolean getOdobrenGodisnji() {
		return odobrenGodisnji;
	}

	public void setOdobrenGodisnji(Boolean odobrenGodisnji) {
		this.odobrenGodisnji = odobrenGodisnji;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDatumOdlaskaNaGodisnji() {
		return datumOdlaskaNaGodisnji;
	}

	public void setDatumOdlaskaNaGodisnji(Date newDatumOdlaskaNaGodisnji) {
		datumOdlaskaNaGodisnji = newDatumOdlaskaNaGodisnji;
	}

	public Date getDatumPovratkaSaGodisnjeg() {
		return datumPovratkaSaGodisnjeg;
	}

	public void setDatumPovratkaSaGodisnjeg(Date newDatumPovratkaSaGodisnjeg) {
		datumPovratkaSaGodisnjeg = newDatumPovratkaSaGodisnjeg;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Recept> recepti) {
		this.recepti = recepti;
	}	

}