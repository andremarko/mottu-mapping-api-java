package com.mottu.mapping.api.repository;

import com.mottu.mapping.api.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository
@Profile("oracle")
public interface UserRepositoryOracle extends JpaRepository<User, Long> {
    @Query(value="SELECT mottu_mapping_pkg.validate_user(:username, :password) FROM DUAL", nativeQuery=true)
    BigDecimal validateUser(String username, String password);
}

