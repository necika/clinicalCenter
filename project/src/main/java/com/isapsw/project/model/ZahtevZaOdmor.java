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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ZahtevZaOdmor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Member of medical staff who sent the request
	@Column(name = "posiljalac", nullable = false)
	private String posiljalac;
	
	// Email of sender
	@Column(name = "email", nullable = false)
	private String email;
	
	// Vacation or time-off ---> two types of paid leave
	@Column(name = "tip_odsustva", nullable = false)
	private String tip;
	
	@Column(name = "pocetak", nullable = false)
	private java.util.Date datumOdlaskaNaGodisnji;
	
	@Column(name = "kraj", nullable = false)
	private java.util.Date datumPovratkaSaGodisnjeg;
	
	@JsonIgnore
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private AdministratorKlinike admin;
	
	public ZahtevZaOdmor() {
		
	}
	
	public ZahtevZaOdmor(String tip, Date datumOdlaskaNaGodisnji,
			Date datumPovratkaSaGodisnjeg, String posiljalac, String email) {
		this.tip = tip;
		this.posiljalac = posiljalac;
		this.datumOdlaskaNaGodisnji = datumOdlaskaNaGodisnji;
		this.datumPovratkaSaGodisnjeg = datumPovratkaSaGodisnjeg;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getPosiljalac() {
		return posiljalac;
	}

	public void setPosiljalac(String posiljalac) {
		this.posiljalac = posiljalac;
	}

	public AdministratorKlinike getAdmin() {
		return admin;
	}

	public void setAdmin(AdministratorKlinike admin) {
		this.admin = admin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
