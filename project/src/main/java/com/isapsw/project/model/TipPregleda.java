package com.isapsw.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/*
 * Model entity representing type of appointment scheduled at chosen clinic
 */
@Entity
@Table(name = "Tip_pregleda")
public class TipPregleda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private java.lang.String naziv;
	
	@Column(name = "opis", nullable = false)
	private java.lang.String opis;

	@Transient
	private Set<Pregled> pregledi = new HashSet<Pregled>();

	@Transient
	private Set<Lekar> lekari = new HashSet<Lekar>();

	public java.lang.String getNaziv() {
		return naziv;
	}

	public void setNaziv(java.lang.String newNaziv) {
		naziv = newNaziv;
	}

	public java.lang.String getOpis() {
		return opis;
	}

	public void setOpis(java.lang.String newOpis) {
		opis = newOpis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public Set<Lekar> getLekari() {
		return lekari;
	}

	public void setLekari(Set<Lekar> lekari) {
		this.lekari = lekari;
	}

}