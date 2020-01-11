package com.isapsw.project.dto;

import com.isapsw.project.model.SifarnikDijagnozaILekova;

public class SifarnikDTO {

	private Long id;
	private String lekovi;
	private String dijagnoze;
	
	public SifarnikDTO() {
		
	}
	
	
	
	public SifarnikDTO(String lekovi, String dijagnoze) {
		super();
		this.lekovi = lekovi;
		this.dijagnoze = dijagnoze;
	}



	public SifarnikDTO(SifarnikDijagnozaILekova s) {
		id = s.getId();
		lekovi = s.getLekovi();
		dijagnoze = s.getDijagnoze();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLekovi() {
		return lekovi;
	}

	public void setLekovi(String lekovi) {
		this.lekovi = lekovi;
	}

	public String getDijagnoze() {
		return dijagnoze;
	}

	public void setDijagnoze(String dijagnoze) {
		this.dijagnoze = dijagnoze;
	}
	
	
}
