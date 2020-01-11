package com.isapsw.project.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.isapsw.project.model.AdministratorKlinike;
import com.isapsw.project.model.IzvestajOPoslovanju;
import com.isapsw.project.model.Klinika;
import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.Pacijent;
import com.isapsw.project.model.Sala;
import com.isapsw.project.model.TipPregleda;

public class KlinikaDTO {

	private Long id;
	private String naziv;
	private String adresa;
	private String opis;
	private Float ocena;
	private ArrayList<String> cenovnik = new ArrayList<String>();
	private Set<Pacijent> pacijent;
	private Set<TipPregleda> tipPregleda;
	private Set<Lekar> lekar;
	private Set<IzvestajOPoslovanju> izvestajOPoslovanju;
	private Set<Sala> sala;
	private Set<AdministratorKlinike> administratorKlinike = new HashSet<AdministratorKlinike>();

	public KlinikaDTO() {
		
	}
	
	public KlinikaDTO(Klinika k) {
		this.id = k.getId();
		this.naziv = k.getNaziv();
		this.adresa = k.getAdresa();
		this.opis = k.getOpis();
		this.ocena = k.getOcena();
		this.cenovnik = k.getCenovnik();
		this.pacijent = k.getPacijent();
		this.tipPregleda = k.getTipPregleda();
		this.lekar = k.getLekar();
		this.izvestajOPoslovanju = k.getIzvestajOPoslovanju();
		this.sala = k.getSale();
		this.administratorKlinike = k.getAdministratorKlinike();
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Float getOcena() {
		return ocena;
	}

	public void setOcena(Float ocena) {
		this.ocena = ocena;
	}

	public Set<Pacijent> getPacijent() {
		return pacijent;
	}

	public void setPacijent(Set<Pacijent> pacijent) {
		this.pacijent = pacijent;
	}

	public Set<TipPregleda> getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(Set<TipPregleda> tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Set<Lekar> getLekar() {
		return lekar;
	}

	public void setLekar(Set<Lekar> lekar) {
		this.lekar = lekar;
	}

	public Set<IzvestajOPoslovanju> getIzvestajOPoslovanju() {
		return izvestajOPoslovanju;
	}

	public void setIzvestajOPoslovanju(
			Set<IzvestajOPoslovanju> izvestajOPoslovanju) {
		this.izvestajOPoslovanju = izvestajOPoslovanju;
	}

	public Set<Sala> getSala() {
		return sala;
	}

	public void setSala(Set<Sala> sala) {
		this.sala = sala;
	}

	public Set<AdministratorKlinike> getAdministratorKlinike() {
		return administratorKlinike;
	}

	public void setAdministratorKlinike(
			Set<AdministratorKlinike> administratorKlinike) {
		this.administratorKlinike = administratorKlinike;
	}

	public ArrayList<String> getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(ArrayList<String> cenovnik) {
		this.cenovnik = cenovnik;
	}

}
