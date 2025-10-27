package com.mottu.mapping.repository;

import com.mottu.mapping.api.model.User;
import com.mottu.mapping.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_shouldReturnUser() {
        Optional<User> result = userRepository.findByUsername("admin");

        assertTrue(result.isPresent(), "The user 'admin' should exist in the database");
        assertEquals("admin", result.get().getUsername(), "The username should be 'admin'");
    }

    @Test
    void findByUsername_shouldReturnEmptyForNonexistentUser() {
        Optional<User> result = userRepository.findByUsername("nonexistent");

        assertTrue(result.isEmpty(), "Nonexistent user should return an empty Optional");
    }

    @Test
    void findByRole_shouldReturnUsers() {
        List<User> admins = userRepository.findByRole("ROLE_ADMIN");

        assertNotNull(admins, "The list should not be null");
        assertFalse(admins.isEmpty(), "There should be at least one ADMIN user");
    }
}
