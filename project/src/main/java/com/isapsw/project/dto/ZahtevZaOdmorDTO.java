package com.isapsw.project.dto;

import com.isapsw.project.model.AdministratorKlinike;
import com.isapsw.project.model.ZahtevZaOdmor;

/*
 * Data Transfer Object class for entity ZahtevZaOdmor
 * (POJO Java class with mandatory properties and get/set methods for given entity)
 */
public class ZahtevZaOdmorDTO {
	
	private Long id;
	private String posiljalac;
	private String email;
	private String tip;
	private java.util.Date datumOdlaskaNaGodisnji;
	private java.util.Date datumPovratkaSaGodisnjeg;
	private AdministratorKlinike admin;
	
	public ZahtevZaOdmorDTO() {
		
	}
	
	public ZahtevZaOdmorDTO(ZahtevZaOdmor zahtev) {
		this.id = zahtev.getId();
		this.posiljalac = zahtev.getPosiljalac();
		this.setEmail(zahtev.getEmail());
		this.tip = zahtev.getTip();
		this.datumOdlaskaNaGodisnji = zahtev.getDatumOdlaskaNaGodisnji();
		this.datumPovratkaSaGodisnjeg = zahtev.getDatumPovratkaSaGodisnjeg();
		this.admin = zahtev.getAdmin();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPosiljalac() {
		return posiljalac;
	}
	public void setPosiljalac(String posiljalac) {
		this.posiljalac = posiljalac;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
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
