package com.mottu.mapping.api.config;

import org.flywaydb.core.Flyway;

public class DatabaseConfig {

    // pega direto no app properties mas é possivel criar DatabaseConfig para criar connection

    private static final String URL = "";

    private static final String USER = "";

    private static final String PASS = "";

    static {
        Flyway flyway = Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .load();
        flyway.migrate();
    }



}
