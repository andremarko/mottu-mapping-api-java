package com.mottu.mapping.repository;

import com.mottu.mapping.api.repository.UserRepositoryOracle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

@DataJpaTest
@ActiveProfiles("oracle")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "oracle")
public class UserRepositoryOracleTest {

    @Autowired
    private UserRepositoryOracle userRepositoryOracle;

    @Test
    void validateExistentAdmin() {
        BigDecimal result = userRepositoryOracle.validateUser("admin" , "admin123");
        assertNotNull(result);
        assertEquals(BigDecimal.ONE, result);
        System.out.println("Existent user: " + result);
    }

    @Test
    void validateExistentOperator() {
        BigDecimal result = userRepositoryOracle.validateUser("operator" , "oper123");
        assertNotNull(result);
        assertEquals(BigDecimal.ONE, result);
        System.out.println("Existent user: " + result);
    }

    @Test
    void validateInexistentUser() {
        BigDecimal result = userRepositoryOracle.validateUser("nonexistent" , "nonexistent123");
        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result);
        System.out.println("Non existent user: " + result);
    }
}
