/***********************************************************************
 * Module:  IzvestajOPoslovanju.java
 * Author:  Boris
 * Purpose: Defines the Class IzvestajOPoslovanju
 ***********************************************************************/

package com.isapsw.project.model;

/** @pdOid cc126153-6d2c-4e30-ac10-67effd56133d */
public class IzvestajOPoslovanju {
	/** @pdOid 734ce9e7-eed8-4d84-9dcf-979ca231fe8d */
	private int prosecnaOcenaKlinike;
	/** @pdOid 5bea7543-3a79-4f22-9044-a426fc7bd2f4 */
	private int prosecnaOcenaLekara;
	/** @pdOid 8d8123c7-5ef3-4096-9361-1c64113d7a75 */
	private java.lang.Double prihodiKlinike;
	/** @pdOid 31568d26-12a4-41e4-a91a-1f4ccf55eb01 */
	private int odrzaniPregledi;

	/** @pdOid f27bc3b1-b5ee-4661-b0e7-4f228f78b0c3 */
	public int getProsecnaOcenaKlinike() {
		return prosecnaOcenaKlinike;
	}

	/**
	 * @param newProsecnaOcenaKlinike
	 * @pdOid d43288dd-da35-4599-ad97-21cc91286687
	 */
	public void setProsecnaOcenaKlinike(int newProsecnaOcenaKlinike) {
		prosecnaOcenaKlinike = newProsecnaOcenaKlinike;
	}

	/** @pdOid 3a1190cc-1d2d-4ed4-ae07-36a79c0c28ca */
	public int getProsecnaOcenaLekara() {
		return prosecnaOcenaLekara;
	}

	/**
	 * @param newProsecnaOcenaLekara
	 * @pdOid e9041697-bfa6-4f30-a71d-71c76a54da6d
	 */
	public void setProsecnaOcenaLekara(int newProsecnaOcenaLekara) {
		prosecnaOcenaLekara = newProsecnaOcenaLekara;
	}

	/** @pdOid 1cefc7cd-712d-4f39-a878-edd439bf6fff */
	public java.lang.Double getPrihodiKlinike() {
		return prihodiKlinike;
	}

	/**
	 * @param newPrihodiKlinike
	 * @pdOid f1a45640-1fb3-4b08-bb20-8967dd2791db
	 */
	public void setPrihodiKlinike(java.lang.Double newPrihodiKlinike) {
		prihodiKlinike = newPrihodiKlinike;
	}

	/** @pdOid 9d7fb53c-35ab-44be-8e70-cec0bd998944 */
	public int getOdrzaniPregledi() {
		return odrzaniPregledi;
	}

	/**
	 * @param newOdrzaniPregledi
	 * @pdOid 92c1ffc4-3de1-4123-b0e3-e6cf77f58db9
	 */
	public void setOdrzaniPregledi(int newOdrzaniPregledi) {
		odrzaniPregledi = newOdrzaniPregledi;
	}

}