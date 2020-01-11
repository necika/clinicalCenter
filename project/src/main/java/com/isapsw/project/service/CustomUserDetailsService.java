package com.isapsw.project.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isapsw.project.model.User;
import com.isapsw.project.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw  new UsernameNotFoundException(String.format("Ne postoji korisnik sa imenom '%s'.", username));
        } else {
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            for (GrantedAuthority auth : user.getAuthorities()) {
//                authorities.add(new SimpleGrantedAuthority(auth.getAuthority()));
//            }
        	return new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.getAuthorities());
        }
    }
    
    /*
     * Password change for all user types
     */
    public void changePassword(String oldPassword, String newPassword) {
    	Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();
		if (authenticationManager != null) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			return;
		}
		
		org.springframework.security.core.userdetails.User userSpring = (org.springframework.security.core.userdetails.User) loadUserByUsername(username);
		User user = userRepository.findByUsername(userSpring.getUsername());
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}
}
