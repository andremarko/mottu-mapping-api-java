package com.mottu.mapping.service;

import com.mottu.mapping.api.dto.request.UserRequestDTO;
import com.mottu.mapping.api.dto.response.UserResponseDTO;
import com.mottu.mapping.api.exception.UserNotFoundException;
import com.mottu.mapping.api.model.User;
import com.mottu.mapping.api.repository.UserRepository;
import com.mottu.mapping.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOperatorUsers_shouldReturnOperatorUsers() {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("op1");
        user1.setPassword("pass1");
        user1.setRole("ROLE_OPERATOR");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUsername("op2");
        user2.setPassword("pass2");
        user2.setRole("ROLE_OPERATOR");

        when(userRepository.findByRole("ROLE_OPERATOR")).thenReturn(List.of(user1, user2));

        List<UserResponseDTO> result = userService.getOperatorUsers();

        assertEquals(2, result.size());
        assertEquals("op1", result.get(0).getUsername());
        assertEquals("op2", result.get(1).getUsername());
    }

    @Test
    void saveOperator_shouldEncodePasswordAndSaveUser() {
        UserRequestDTO request = new UserRequestDTO();
        request.setUsername("newOp");
        request.setPassword("123456");

        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setUsername("newOp");
        savedUser.setPassword("encoded123");
        savedUser.setRole("ROLE_OPERATOR");

        when(passwordEncoder.encode("123456")).thenReturn("encoded123");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDTO response = userService.saveOperator(request);

        assertEquals(1L, response.getUserId());
        assertEquals("newOp", response.getUsername());
        assertEquals("encoded123", response.getPassword());
        assertEquals("ROLE_OPERATOR", response.getRole());

        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteOperator_shouldDeleteExistingUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteOperator(1L));

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteOperator_shouldThrowWhenUserNotFound() {
        when(userRepository.existsById(999L)).thenReturn(false);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.deleteOperator(999L));

        assertEquals("User ID 999 not found.", exception.getMessage());
    }
}
