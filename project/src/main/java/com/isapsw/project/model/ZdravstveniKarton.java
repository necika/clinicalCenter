/***********************************************************************
 * Module:  ZdravstveniKarton.java
 * Author:  Boris
 * Purpose: Defines the Class ZdravstveniKarton
 ***********************************************************************/

package com.isapsw.project.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ZdravstveniKarton")
public class ZdravstveniKarton {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "osnovniPodaci", nullable = false)
	private String osnovniPodaci;
	
	@JsonIgnore
	@OneToMany(mappedBy = "zdravstveniKarton", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<IzvestajOPregledu> izvestajOPregledu;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<IzvestajOPregledu> getIzvestajOPregledu() {
		return izvestajOPregledu;
	}

	public void setIzvestajOPregledu(List<IzvestajOPregledu> izvestajOPregledu) {
		this.izvestajOPregledu = izvestajOPregledu;
	}
	
	public String getOsnovniPodaci() {
		return osnovniPodaci;
	}

	public void setOsnovniPodaci(String osnovniPodaci) {
		this.osnovniPodaci = osnovniPodaci;
	}
	

}