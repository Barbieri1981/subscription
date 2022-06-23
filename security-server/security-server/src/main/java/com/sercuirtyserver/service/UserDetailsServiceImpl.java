package com.sercuirtyserver.service;

import com.sercuirtyserver.entity.Rol;
import com.sercuirtyserver.entity.SubscriptionUser;
import com.sercuirtyserver.repository.RolRepository;
import com.sercuirtyserver.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;
	private RolRepository rolRepository;

	public UserDetailsServiceImpl(UserRepository userRepository,
                                  RolRepository rolRepository) {
		this.userRepository = userRepository;
		this.rolRepository = rolRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		SubscriptionUser user = userRepository.findByUserNameIgnoringCase(userName);
		if (user == null) {
			throw new UsernameNotFoundException(userName);
		}

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getUserRols().stream().forEach(r -> {
			Optional<Rol> rol = rolRepository.findById(r.getRolId());
			authorities.add(new SimpleGrantedAuthority("ROLE_"+rol.get().getDescription()));
		});


		return new User(user.getUserName(), user.getPassword(), authorities);
	}
}
