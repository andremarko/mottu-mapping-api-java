package com.mottu.mapping.api.repository;

import com.mottu.mapping.api.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> { }
