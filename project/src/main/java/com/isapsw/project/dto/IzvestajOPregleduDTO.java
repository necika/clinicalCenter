package com.isapsw.project.dto;

import java.util.List;

import com.isapsw.project.model.IzvestajOPregledu;
import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Pacijent;
import com.isapsw.project.model.Recept;
import com.isapsw.project.model.ZdravstveniKarton;

public class IzvestajOPregleduDTO {

	private Long id;
	private Lekar lekar;
	private Pacijent pacijent;
	private List<Recept> recept;
	private ZdravstveniKarton zdravstveniKarton;
	private String podaci;
	
	public IzvestajOPregleduDTO() {
		
	}

	public IzvestajOPregleduDTO(ZdravstveniKarton zdravstveniKarton,String podaci) {
		super();
		this.lekar = lekar;
		this.pacijent = pacijent;
		this.recept = recept;
		this.zdravstveniKarton = zdravstveniKarton;
		this.podaci = podaci;
	}
	
	public IzvestajOPregleduDTO(IzvestajOPregledu iop) {
		this.id = iop.getId();
		this.lekar = iop.getLekar();
		this.pacijent = iop.getPacijent();
		this.recept = iop.getRecept();
		this.zdravstveniKarton = iop.getZdravstveniKarton();
		this.podaci = iop.getPodaci();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Recept> getRecept() {
		return recept;
	}

	public void setRecept(List<Recept> recept) {
		this.recept = recept;
	}

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKarton zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
	}

	public String getPodaci() {
		return podaci;
	}

	public void setPodaci(String podaci) {
		this.podaci = podaci;
	}
	
}
