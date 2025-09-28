package com.mottu.mapping.api.service;

import com.mottu.mapping.api.dto.request.UserRequestDTO;
import com.mottu.mapping.api.dto.response.UserResponseDTO;
import com.mottu.mapping.api.exception.UserNotFoundException;
import com.mottu.mapping.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.mottu.mapping.api.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    public List<UserResponseDTO> getOperatorUsers() {
        List<User> users = userRepository.findByRole("ROLE_OPERATOR");
        return users.stream()
                .map(u -> new UserResponseDTO(u.getUserId(), u.getUsername(), u.getPassword(), u.getRole()))
                .toList();
    }

    public UserResponseDTO saveOperator(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ROLE_OPERATOR");
        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser.getUserId(), savedUser.getUsername(), savedUser.getPassword(), savedUser.getRole());
    }

    public void deleteOperator(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID " + userId + " not found.");
        }
        userRepository.deleteById(userId);
    }
}
