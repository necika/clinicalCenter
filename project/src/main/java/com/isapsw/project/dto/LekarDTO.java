package com.isapsw.project.dto;

import com.isapsw.project.model.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LekarDTO {
    private Long id;
    private String email;
    private String lozinka;
    private String ime;
    private String prezime;
    private String adresa;
    private String grad;
    private String drzava;
    private String brTelefona;
    private int jedinstveniBrOsiguranika;
    private float ocena;
    private java.util.Date datumOdlaskaNaGodisnji;
    private java.util.Date datumPovratkaSaGodisnjeg;
    private String pocetakRadnogVremena;
    private String krajRadnogVremena;
    private Set<Pregled> pregledi = new HashSet<Pregled>();
    private java.util.List<IzvestajOPregledu> izvestajOPregledu;
    private java.util.List<Operacija> operacija;
    private Klinika klinika;
    private SifarnikDijagnozaILekova sifarnikDijagnozaILekova;

    public LekarDTO() {

    }

    public LekarDTO(Long id, String email, String lozinka, String ime, String prezime, String adresa, String grad,
                    String drzava, String brTelefona, int jedinstveniBrOsiguranika, float ocena,
                    String pocetakRadnogVremena, String krajRadnogVremena) {
        this.id = id;
        this.email = email;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.grad = grad;
        this.drzava = drzava;
        this.brTelefona = brTelefona;
        this.jedinstveniBrOsiguranika = jedinstveniBrOsiguranika;
        this.ocena = ocena;
        this.pocetakRadnogVremena = pocetakRadnogVremena;
        this.krajRadnogVremena = krajRadnogVremena;
    }

    public LekarDTO(Long id, String email, String lozinka, String ime, String prezime, String adresa, String grad,
                    String drzava, String brTelefona, int jedinstveniBrOsiguranika, float ocena) {
        this.id = id;
        this.email = email;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.grad = grad;
        this.drzava = drzava;
        this.brTelefona = brTelefona;
        this.jedinstveniBrOsiguranika = jedinstveniBrOsiguranika;
        this.ocena = ocena;
    }

    public LekarDTO(Lekar l) {
    	this.id = l.getId();
        this.email = l.getEmail();
        this.lozinka = l.getLozinka();
        this.ime = l.getIme();
        this.prezime = l.getPrezime();
        this.adresa = l.getAdresa();
        this.grad = l.getGrad();
        this.drzava = l.getDrzava();
        this.brTelefona = l.getBrTelefona();
        this.jedinstveniBrOsiguranika = l.getJedinstveniBrOsiguranika();
        this.ocena = l.getOcena();
        this.datumOdlaskaNaGodisnji = l.getDatumOdlaskaNaGodisnji();
        this.datumPovratkaSaGodisnjeg = l.getDatumPovratkaSaGodisnjeg();
        this.pocetakRadnogVremena = l.getPocetakRadnogVremena();
        this.krajRadnogVremena = l.getKrajRadnogVremena();
        this.pregledi = l.getPregledi();
        this.klinika = l.getKlinika();
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

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
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

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }

    public Date getDatumOdlaskaNaGodisnji() {
        return datumOdlaskaNaGodisnji;
    }

    public void setDatumOdlaskaNaGodisnji(Date datumOdlaskaNaGodisnji) {
        this.datumOdlaskaNaGodisnji = datumOdlaskaNaGodisnji;
    }

    public Date getDatumPovratkaSaGodisnjeg() {
        return datumPovratkaSaGodisnjeg;
    }

    public void setDatumPovratkaSaGodisnjeg(Date datumPovratkaSaGodisnjeg) {
        this.datumPovratkaSaGodisnjeg = datumPovratkaSaGodisnjeg;
    }

    public String getPocetakRadnogVremena() {
        return pocetakRadnogVremena;
    }

    public void setPocetakRadnogVremena(String pocetakRadnogVremena) {
        this.pocetakRadnogVremena = pocetakRadnogVremena;
    }

    public String getKrajRadnogVremena() {
        return krajRadnogVremena;
    }

    public void setKrajRadnogVremena(String krajRadnogVremena) {
        this.krajRadnogVremena = krajRadnogVremena;
    }

    public List<IzvestajOPregledu> getIzvestajOPregledu() {
        return izvestajOPregledu;
    }

    public void setIzvestajOPregledu(List<IzvestajOPregledu> izvestajOPregledu) {
        this.izvestajOPregledu = izvestajOPregledu;
    }

    public List<Operacija> getOperacija() {
        return operacija;
    }

    public void setOperacija(List<Operacija> operacija) {
        this.operacija = operacija;
    }

    public Klinika getKlinika() {
        return klinika;
    }

    public void setKlinika(Klinika klinika) {
        this.klinika = klinika;
    }

    public SifarnikDijagnozaILekova getSifarnikDijagnozaILekova() {
        return sifarnikDijagnozaILekova;
    }

    public void setSifarnikDijagnozaILekova(SifarnikDijagnozaILekova sifarnikDijagnozaILekova) {
        this.sifarnikDijagnozaILekova = sifarnikDijagnozaILekova;
    }

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}
}
