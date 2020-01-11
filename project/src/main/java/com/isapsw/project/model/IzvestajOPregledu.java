
package com.isapsw.project.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "IzvestajOPregledu")
public class IzvestajOPregledu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Transient
	private Lekar lekar;

	@Transient
	private Pacijent pacijent;

	@Column(name = "podaci", nullable = false)
	private String podaci;
	
	@JsonIgnore
	@OneToMany(mappedBy = "izvestajOPregledu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Recept> recept;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private ZdravstveniKarton zdravstveniKarton;

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKarton zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
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

	public String getPodaci() {
		return podaci;
	}

	public void setPodaci(String podaci) {
		this.podaci = podaci;
	}
	

}