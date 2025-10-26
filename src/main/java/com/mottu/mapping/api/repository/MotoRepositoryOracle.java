package com.mottu.mapping.api.repository;

import com.mottu.mapping.api.model.Moto;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
@Profile("oracle")
public interface MotoRepositoryOracle extends JpaRepository<Moto, Long> {
    @Procedure(procedureName = "mottu_mapping_pkg.proc_join_json")
    String procJoinJson();
}
