package com.redabensaad.connectude.services;

import com.redabensaad.connectude.datasource.entities.PapelEntity;
import com.redabensaad.connectude.datasource.repositories.PapelRepository;
import com.redabensaad.connectude.interfaces.PapelInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PapelService implements PapelInterface {

    @Autowired
    private PapelRepository papelRepository;

    public Optional<PapelEntity> buscarPapelPorId(Long id){
        return papelRepository.findById(id);
    }

}
