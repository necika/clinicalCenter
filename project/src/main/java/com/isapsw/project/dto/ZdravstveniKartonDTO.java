package com.isapsw.project.dto;

import java.util.List;

import com.isapsw.project.model.IzvestajOPregledu;
import com.isapsw.project.model.Pacijent;
import com.isapsw.project.model.ZdravstveniKarton;

public class ZdravstveniKartonDTO {

	private Long id;
	private String osnovniPodaci;
	private List<IzvestajOPregledu> izvestajOPregledu;
	
	public ZdravstveniKartonDTO() {
		
	}

	public ZdravstveniKartonDTO(Long id, String osnovniPodaci, Pacijent pacijent,
			List<IzvestajOPregledu> izvestajOPregledu) {
		this.id = id;
		this.osnovniPodaci = osnovniPodaci;
		this.izvestajOPregledu = izvestajOPregledu;
	}
	
	public ZdravstveniKartonDTO(ZdravstveniKarton zk) {
		this.id = zk.getId();
		this.osnovniPodaci = zk.getOsnovniPodaci();
		this.izvestajOPregledu = zk.getIzvestajOPregledu();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOsnovniPodaci() {
		return osnovniPodaci;
	}

	public void setOsnovniPodaci(String osnovniPodaci) {
		this.osnovniPodaci = osnovniPodaci;
	}


	public List<IzvestajOPregledu> getIzvestajOPregledu() {
		return izvestajOPregledu;
	}

	public void setIzvestajOPregledu(List<IzvestajOPregledu> izvestajOPregledu) {
		this.izvestajOPregledu = izvestajOPregledu;
	}
	
	
}
