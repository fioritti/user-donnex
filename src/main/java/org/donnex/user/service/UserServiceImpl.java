package org.donnex.user.service;

import java.util.Arrays;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.donnex.user.model.Role;
import org.donnex.user.model.User;
import org.donnex.user.repository.RoleRepository;
import org.donnex.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl {
	
	@PostConstruct
	public void init() {
		Role role = new Role();
		role.setRole("ADMIN");
		roleRepository.save(role);
	}

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	public User loadUserByUsername(String username) {
		return userRepository.findByEmail(username);
	}


}
