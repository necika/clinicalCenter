package com.isapsw.project.dto;

import com.isapsw.project.model.IzvestajOPregledu;
import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Recept;
import com.isapsw.project.model.SifarnikDijagnozaILekova;

public class ReceptDTO {

	private Long id;
	private String nazivRecepta;
	private Boolean overen;
	private IzvestajOPregledu izvestajOPregledu;
	private SifarnikDijagnozaILekova sifarnik;
	private MedicinskaSestra sestra;
	
	public ReceptDTO() {
		
	}

	public ReceptDTO(Long id, String nazivRecepta, Boolean overen, IzvestajOPregledu izvestajOPregledu,SifarnikDijagnozaILekova sifarnik,MedicinskaSestra sestra) {
		super();
		this.id = id;
		this.nazivRecepta = nazivRecepta;
		this.overen = overen;
		this.izvestajOPregledu = izvestajOPregledu;
		this.sifarnik = sifarnik;
		this.sestra = sestra;
	}
	
	public ReceptDTO(Recept r) {
		this.id = r.getId();
		this.nazivRecepta = r.getNazivRecepta();
		this.overen = r.getOveren();
		this.izvestajOPregledu = r.getIzvestajOPregledu();
		this.sifarnik = r.getSifarnik();
		this.sestra = r.getSestra();
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

	public Boolean getOveren() {
		return overen;
	}

	public void setOveren(Boolean overen) {
		this.overen = overen;
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
