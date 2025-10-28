package com.mottu.mapping.api.service;

import com.mottu.mapping.api.dto.request.UserRequestDTO;
import com.mottu.mapping.api.dto.response.UserResponseDTO;
import com.mottu.mapping.api.exception.UserNotFoundException;
import com.mottu.mapping.api.repository.UserRepository;
import com.mottu.mapping.api.repository.UserRepositoryOracle;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.mottu.mapping.api.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired(required = false)
//    private UserRepositoryOracle userRepositoryOracle;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

//    public boolean validateUser(String username, String password) {
//        if (userRepositoryOracle == null) {
//            throw new UnsupportedOperationException("ValidateUser only supported for Oracle persistence");
//        }
//        BigDecimal result = userRepositoryOracle.validateUser(username, password);
//        return result != null && result.intValue() == 1;
//    }

    // lista todos operadores
    public List<UserResponseDTO> getOperatorUsers() {
        List<User> users = userRepository.findByRole("ROLE_OPERATOR");
        return users.stream()
                .map(u -> new UserResponseDTO(u.getUserId(), u.getUsername(), u.getPassword(), u.getRole()))
                .toList();
    }

    // usado para admin cadastrar operadores
    public UserResponseDTO saveOperator(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ROLE_OPERATOR");
        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser.getUserId(), savedUser.getUsername(), savedUser.getPassword(), savedUser.getRole());
    }

    // admin deleta operadores
    public void deleteOperator(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID " + userId + " not found.");
        }
        userRepository.deleteById(userId);
    }
}
