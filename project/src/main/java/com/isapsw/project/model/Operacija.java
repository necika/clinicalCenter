/***********************************************************************
 * Module:  Operacija.java
 * Author:  Boris
 * Purpose: Defines the Class Operacija
 ***********************************************************************/

package com.isapsw.project.model;

/** @pdOid 17d4a62c-0508-4b63-a0f5-e3b3e8e07ea8 */
public class Operacija {
	/** @pdOid 00f49435-1353-431e-8254-a6ee956e152e */
	private java.util.Date datumVreme;
	/** @pdOid 306f38a7-9f9c-4abd-9907-4d0ed341a7f1 */
	private int trajanje;
	/** @pdOid 81930b18-870a-48d4-85e7-4d5055cae17c */
	private java.lang.Double cena;

	/** @pdRoleInfo migr=no name=Sala assc=association17 mult=1..1 */
	private Sala sala;
	/**
	 * @pdRoleInfo migr=no name=Lekar assc=association16 coll=java.util.List
	 *             impl=java.util.ArrayList mult=1..* side=A
	 */
	private java.util.List<Lekar> lekar;

	/** @pdGenerated default parent getter */
	public Sala getSala() {
		return sala;
	}

	/** @pdGenerated default getter */
	public java.util.List<Lekar> getLekar() {
		if (lekar == null)
			lekar = new java.util.ArrayList<Lekar>();
		return lekar;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorLekar() {
		if (lekar == null)
			lekar = new java.util.ArrayList<Lekar>();
		return lekar.iterator();
	}

	/** @pdOid b6f35fe3-7540-4977-92d0-139313b12727 */
	public java.util.Date getDatumVreme() {
		return datumVreme;
	}

	/**
	 * @param newDatumVreme
	 * @pdOid 60a3bfa3-6989-4659-9cf6-3d89e2678763
	 */
	public void setDatumVreme(java.util.Date newDatumVreme) {
		datumVreme = newDatumVreme;
	}

	/** @pdOid 59806b6b-5e2c-4b0c-92f0-7eac4764bfa2 */
	public int getTrajanje() {
		return trajanje;
	}

	/**
	 * @param newTrajanje
	 * @pdOid fbf72e13-9707-464d-995a-c9038faf0772
	 */
	public void setTrajanje(int newTrajanje) {
		trajanje = newTrajanje;
	}

	/** @pdOid a3b35c72-ba30-4f76-993c-6b5bbb70fa98 */
	public java.lang.Double getCena() {
		return cena;
	}

	/**
	 * @param newCena
	 * @pdOid b9a676f7-65b6-4fd2-908c-33b3487f2d0a
	 */
	public void setCena(java.lang.Double newCena) {
		cena = newCena;
	}

}