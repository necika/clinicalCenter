/***********************************************************************
 * Module:  SifarnikDijagnozaILekova.java
 * Author:  Boris
 * Purpose: Defines the Class SifarnikDijagnozaILekova
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Sifarnik")
public class SifarnikDijagnozaILekova {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "lekovi", nullable = false)
	private java.lang.String lekovi;

	@Column(name = "dijagnoze", nullable = false)
	private java.lang.String dijagnoze;

	public java.lang.String getLekovi() {
		return lekovi;
	}


	public void setLekovi(java.lang.String newLekovi) {
		lekovi = newLekovi;
	}


	public java.lang.String getDijagnoze() {
		return dijagnoze;
	}


	public void setDijagnoze(java.lang.String newDijagnoze) {
		dijagnoze = newDijagnoze;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}