package com.mottu.mapping.repository;

/*
* Integration Test - procedure may exist
* */

import com.mottu.mapping.api.repository.MotoRepositoryOracle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("oracle")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "oracle")
public class MotoRepositoryOracleTest {

    @Autowired
    private MotoRepositoryOracle motoRepositoryOracle;

    @Test
    void executeProcJoinJson() {
        String result = motoRepositoryOracle.procJoinJson();
        assertNotNull(result);
        System.out.println("Procedure: " + "\n" + result);
    }
}
