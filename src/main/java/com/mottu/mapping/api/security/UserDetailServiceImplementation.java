package com.mottu.mapping.api.security;

import com.mottu.mapping.api.exception.UserNotFoundException;
import com.mottu.mapping.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.mottu.mapping.api.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        String role = userEntity.getRole();
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        return new org.springframework.security.core.userdetails.User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }

}
