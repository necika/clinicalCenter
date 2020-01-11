package com.isapsw.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.isapsw.project.dto.UserDTO;
import com.isapsw.project.model.User;
import com.isapsw.project.service.UserService;

@RestController
@RequestMapping(value = "/isa/korisnik")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public User loadById(@PathVariable Long userId) {
        return this.userService.findById(userId);
    }

    @GetMapping(value = "/{korisnickoIme}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<UserDTO> loadByUsername(@PathVariable String korisnickoIme) {
        User user = userService.findByUsername(korisnickoIme);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "/svi", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> loadAll() {
        List<User> users = userService.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User u : users) {
            userDTOS.add(new UserDTO(u));
        }

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }
}
