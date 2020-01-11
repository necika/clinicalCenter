package com.isapsw.project.dto;

import com.isapsw.project.model.Klinika;
import com.isapsw.project.model.Sala;

/*
 * Data Transfer Object class for entity Sala
 * (POJO Java class with mandatory properties and get/set methods for given entity)
 */
public class SalaDTO {

	private Long id;
	private int broj;
	private String naziv;
	private Klinika klinika;

	public SalaDTO() {

	}

	public SalaDTO(Sala sala) {
		this.id = sala.getId();
		this.broj = sala.getBroj();
		this.naziv = sala.getNaziv();
		this.klinika = sala.getKlinika();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBroj() {
		return broj;
	}

	public void setBroj(int broj) {
		this.broj = broj;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
}
