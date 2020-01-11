package com.isapsw.project.dto;

import java.util.List;

import com.isapsw.project.model.Authority;
import com.isapsw.project.model.User;

public class UserDTO {
	private Long id;
	private String email;
	private String username;
	private String password;
	private String ime;
	private String prezime;
	private boolean enabled;
	private String drzava;
	private String grad;
	private String brTelefona;
	private int jedinstveniBrOsiguranika;
	private String adresa;
	private List<Authority> authorities;
	private String odobrenaRegistracija;

	public UserDTO(User u) {
		id = u.getId();
		email = u.getEmail();
		username = u.getUsername();
		password = u.getPassword();
		ime = u.getIme();
		prezime = u.getPrezime();
		enabled = u.isEnabled();
		drzava = u.getDrzava();
		brTelefona = u.getBrTelefona();
		jedinstveniBrOsiguranika = u.getJedinstveniBrOsiguranika();
		adresa = u.getAdresa();
		odobrenaRegistracija = u.getOdobrenaRegistracija();
	}

	public UserDTO() {

	}

	public UserDTO(String email, String username, String password, String ime, String prezime, boolean enabled,
			String drzava, String grad, String brTelefona, int jedinstveniBrOsiguranika, String adresa,
			List<Authority> authorities, String odobrenaRegistracija) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.ime = ime;
		this.prezime = prezime;
		this.enabled = enabled;
		this.drzava = drzava;
		this.grad = grad;
		this.brTelefona = brTelefona;
		this.jedinstveniBrOsiguranika = jedinstveniBrOsiguranika;
		this.adresa = adresa;
		this.authorities = authorities;
		this.odobrenaRegistracija = odobrenaRegistracija;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getBrTelefona() {
		return brTelefona;
	}

	public void setBrTelefona(String brTelefona) {
		this.brTelefona = brTelefona;
	}

	public int getJedinstveniBrOsiguranika() {
		return jedinstveniBrOsiguranika;
	}

	public void setJedinstveniBrOsiguranika(int jedinstveniBrOsiguranika) {
		this.jedinstveniBrOsiguranika = jedinstveniBrOsiguranika;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getOdobrenaRegistracija() {
		return odobrenaRegistracija;
	}

	public void setOdobrenaRegistracija(String odobrenaRegistracija) {
		this.odobrenaRegistracija = odobrenaRegistracija;
	}

}
