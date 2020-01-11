/***********************************************************************
 * Module:  Recept.java
 * Author:  Boris
 * Purpose: Defines the Class Recept
 ***********************************************************************/

package com.isapsw.project.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Recept")
public class Recept {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nazivRecepta", nullable = false)
	private String nazivRecepta;
	
	@Column(name = "overen", nullable = false)
	private Boolean overen;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private IzvestajOPregledu izvestajOPregledu;
	
	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private MedicinskaSestra sestra;
	
	@OneToOne
	private SifarnikDijagnozaILekova sifarnik;
	
	public java.lang.Boolean getOveren() {
		return overen;
	}

	public void setOveren(java.lang.Boolean newOveren) {
		overen = newOveren;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNazivRecepta() {
		return nazivRecepta;
	}

	public void setNazivRecepta(String nazivRecepta) {
		this.nazivRecepta = nazivRecepta;
	}

	public IzvestajOPregledu getIzvestajOPregledu() {
		return izvestajOPregledu;
	}

	public void setIzvestajOPregledu(IzvestajOPregledu izvestajOPregledu) {
		this.izvestajOPregledu = izvestajOPregledu;
	}

	public SifarnikDijagnozaILekova getSifarnik() {
		return sifarnik;
	}

	public void setSifarnik(SifarnikDijagnozaILekova sifarnik) {
		this.sifarnik = sifarnik;
	}

	public MedicinskaSestra getSestra() {
		return sestra;
	}

	public void setSestra(MedicinskaSestra sestra) {
		this.sestra = sestra;
	}
	
	
}