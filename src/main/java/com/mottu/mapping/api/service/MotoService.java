package com.mottu.mapping.api.service;

import com.mottu.mapping.api.model.Moto;
import com.mottu.mapping.api.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MotoService {
    @Autowired
    private MotoRepository motoRepository;

    public Moto save(Moto moto) {
        return motoRepository.save(moto);
    }



}
