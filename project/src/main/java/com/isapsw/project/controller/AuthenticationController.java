package com.isapsw.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.isapsw.project.dto.PacijentDTO;
import com.isapsw.project.exception.ResourceConflictException;
import com.isapsw.project.model.Lekar;
import com.isapsw.project.model.MedicinskaSestra;
import com.isapsw.project.model.Pregled;
import com.isapsw.project.model.User;
import com.isapsw.project.model.UserRequest;
import com.isapsw.project.model.UserTokenState;
import com.isapsw.project.security.TokenUtils;
import com.isapsw.project.security.auth.JwtAuthenticationRequest;
import com.isapsw.project.service.CustomUserDetailsService;
import com.isapsw.project.service.LekarService;
import com.isapsw.project.service.MedicinskaSestraService;
import com.isapsw.project.service.PregledService;
import com.isapsw.project.service.UserService;

@RestController
@RequestMapping(value = "/isa", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private MedicinskaSestraService sestraService;

	@Autowired
	private PregledService pregledService;

	@Autowired
	private LekarService lekarService;

	@RequestMapping(value = "/prijava", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication
				.getPrincipal();

		User userEnabled = userService.findByUsername(user.getUsername());
		if (!userEnabled.getOdobrenaRegistracija().equals("prihvacen")) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		return ResponseEntity.ok(new UserTokenState(jwt, (long) expiresIn));
	}

	@RequestMapping(value = "/registracija", method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) {
		User existUser = this.userService.findByUsername(userRequest.getUsername());
		if (existUser != null) {
			throw new ResourceConflictException(userRequest.getId(), "Email je vec u upotrebi.");
		}

		User user = this.userService.save(userRequest);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/isa/korisnik/{userId}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/passwordChange", method = RequestMethod.POST)
	@PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}

	@GetMapping(value = "/pacijentiUlogovanogKorisnika", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('NURSE') or hasRole('DOCTOR')")
	public ResponseEntity<List<PacijentDTO>> getLekari() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User u = userService.findByUsername(authentication.getName());
		Lekar lekar = lekarService.findByJedinstveniBrOsiguranika(u.getJedinstveniBrOsiguranika());
		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		if (lekar == null) {
			MedicinskaSestra sestra = sestraService.findByJedinstveniBrOsiguranika(u.getJedinstveniBrOsiguranika());
			List<Pregled> pregledi = pregledService.findAll();
			for (Pregled p : pregledi) {
				if (sestra.getId() == p.getMedicinskaSestra().getId()) {
					if (p.getPacijent() != null) {
						p.getPacijent().setIme(p.getPacijent().getUser().getIme());
						p.getPacijent().setPrezime(p.getPacijent().getUser().getPrezime());
						p.getPacijent().setAdresa(p.getPacijent().getUser().getAdresa());
						p.getPacijent().setBrTelefona(p.getPacijent().getUser().getBrTelefona());
						p.getPacijent().setDrzava(p.getPacijent().getUser().getDrzava());
						p.getPacijent().setGrad(p.getPacijent().getUser().getGrad());
						p.getPacijent().setEmail(p.getPacijent().getUser().getEmail());
						p.getPacijent().setLozinka(p.getPacijent().getUser().getPassword());
						pacijentiDTO.add(new PacijentDTO(p.getPacijent()));
					}
				}
			}
		} else {
			List<Pregled> pregledi = pregledService.findAll();
			for (Pregled p : pregledi) {
				if (lekar.getId() == p.getLekar().getId()) {
					if (p.getPacijent() != null) {
						p.getPacijent().setIme(p.getPacijent().getUser().getIme());
						p.getPacijent().setPrezime(p.getPacijent().getUser().getPrezime());
						p.getPacijent().setAdresa(p.getPacijent().getUser().getAdresa());
						p.getPacijent().setBrTelefona(p.getPacijent().getUser().getBrTelefona());
						p.getPacijent().setDrzava(p.getPacijent().getUser().getDrzava());
						p.getPacijent().setGrad(p.getPacijent().getUser().getGrad());
						p.getPacijent().setEmail(p.getPacijent().getUser().getEmail());
						p.getPacijent().setLozinka(p.getPacijent().getUser().getPassword());
						pacijentiDTO.add(new PacijentDTO(p.getPacijent()));
					}
				}
			}
		}

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}

}
