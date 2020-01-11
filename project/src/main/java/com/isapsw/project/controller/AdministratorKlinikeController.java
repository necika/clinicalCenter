package com.isapsw.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isapsw.project.dto.AdministratorKlinikeDTO;
import com.isapsw.project.dto.ZahtevZaOdmorDTO;
import com.isapsw.project.dto.ZahtevZaTerminDTO;
import com.isapsw.project.service.AdministratorKlinikeService;
import com.isapsw.project.service.UserService;

@RestController
@RequestMapping(value = "isa/adminKlinike")
public class AdministratorKlinikeController {

	@Autowired
	private AdministratorKlinikeService adminService;

	@Autowired
	private UserService userService;

	/*
	 * Sending request for getting all the tuples in table AdministratorKlinike
	 * and feeding it to the front-end part of application
	 */
	@GetMapping(value = "/sviZahteviZaPreglede")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ZahtevZaTerminDTO>> sveTerminiZaPregledee() {
		return adminService.sveTerminiZaPregledeService();
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<AdministratorKlinikeDTO>> getAdmini() {
		return adminService.getAvailableAdmins();
	}

	/*
	 * Adding new tuple in database table AdministratorKlinike based on received
	 * data from front-end part of application
	 */
	@PostMapping(value = "/dodaj/{klinika}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdministratorKlinikeDTO> dodavanjeNovogAdministratoraKlinike(
			@PathVariable String klinika,
			@RequestBody AdministratorKlinikeDTO adminDTO) {
		return adminService.addAdmin(adminDTO, klinika);
	}

	/*
	 * Sending request to database table AdministratorKlinike for tuple
	 * specified by given ID
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdministratorKlinikeDTO> getAdmin(@PathVariable Long id) {
		return adminService.getAdminById(id);
	}

	/*
	 * Receiving data from front-end part of application and updating the
	 * existing tuple in table AdministratorKlinike
	 */
	@PutMapping(value = "/update/{id}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdministratorKlinikeDTO> updateAdmin(@RequestBody AdministratorKlinikeDTO adminDTO) {
		return adminService.updateAdmin(adminDTO);
	}

	/*
	 * Receiving new password from front-end part of application and updating
	 * the existing tuple in table AdministratorKlinike
	 */
	@PutMapping(value = "/change-pass/{lozinka}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdministratorKlinikeDTO> updatePass(@PathVariable String lozinka) {
		return adminService.changePassword(lozinka);
	}
	
	/*
	 * Getting all vacation/time-off requests sent to administrator
	 */
	@GetMapping(value = "/{adminId}/odmor")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ZahtevZaOdmorDTO>> getZahteviZaOdmor(
			@PathVariable Long adminId) {
		return adminService.getVacationRequests(adminId);
	}
	
	/*
	 * Sending response to vacation/time-off request sent by doctor/nurse
	 */
	@PutMapping(value = "/odmor/{odgovor}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ZahtevZaOdmorDTO> slanjeOdgovoraZaOdmor(
			@PathVariable String odgovor,
			@RequestBody ZahtevZaOdmorDTO zahtevDTO) {
		return adminService.sendVacationResponse(zahtevDTO, odgovor);
	}

}
