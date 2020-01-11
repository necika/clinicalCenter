package com.isapsw.project.dto;

import java.util.HashSet;
import java.util.Set;

import com.isapsw.project.model.Pregled;
import com.isapsw.project.model.TipPregleda;

/*
 * Data Transfer Object class for entity TipPregleda
 * (POJO Java class with mandatory properties and get/set methods for given entity)
 */
public class TipPregledaDTO {

	private Long id;
	private String naziv;
	private String opis;
	private Set<Pregled> pregledi = new HashSet<Pregled>();
	
	public TipPregledaDTO() {
		
	}

	public TipPregledaDTO(Long id, String naziv, String opis, Set<Pregled> pregledi) {
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.setPregledi(pregledi);
	}
	
	public TipPregledaDTO(TipPregleda t) {
		this.id = t.getId();
		this.naziv = t.getNaziv();
		this.opis = t.getOpis();
		this.setPregledi(t.getPregledi());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}
	
}
