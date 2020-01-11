package com.isapsw.project.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ZahtevZaTermin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "datum", nullable = false)
	private Date datum;
	
	@Column(name = "pocetak", nullable = false)
	private String pocetak;
	
	@Column(name = "kraj", nullable = false)
	private String kraj;
	
	@Column(name = "tipPregleda", nullable = false)
	private Long tipPregleda;
	
	@Column(name = "tipTermina", nullable = false)
	private String tipTermina;
	
	@JsonIgnore
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private AdministratorKlinike admin;
	
	@OneToOne
	private Pacijent pacijent;
	
	@OneToOne
	private MedicinskaSestra sestra;
	
	@OneToOne
	private Lekar lekar;
	
	public ZahtevZaTermin() {
		
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getPocetak() {
		return pocetak;
	}

	public void setPocetak(String pocetak) {
		this.pocetak = pocetak;
	}

	public String getKraj() {
		return kraj;
	}

	public void setKraj(String kraj) {
		this.kraj = kraj;
	}

	public Long getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(Long tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public String getTipTermina() {
		return tipTermina;
	}

	public void setTipTermina(String tipTermina) {
		this.tipTermina = tipTermina;
	}

	public AdministratorKlinike getAdmin() {
		return admin;
	}

	public void setAdmin(AdministratorKlinike admin) {
		this.admin = admin;
	}

	public MedicinskaSestra getSestra() {
		return sestra;
	}

	public void setSestra(MedicinskaSestra sestra) {
		this.sestra = sestra;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}
	
}
