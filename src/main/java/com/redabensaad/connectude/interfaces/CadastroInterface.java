package com.redabensaad.connectude.interfaces;

import com.redabensaad.connectude.controllers.dtos.requests.RequestCadastroDTO;
import com.redabensaad.connectude.controllers.dtos.responses.ResponseCadastroDTO;
import com.redabensaad.connectude.datasource.entities.CadastroEntity;
import com.redabensaad.connectude.datasource.entities.PapelEntity;

import java.util.List;

public interface CadastroInterface {


    CadastroEntity criarCadastro(RequestCadastroDTO requestCadastroDTO);

    List<CadastroEntity> listarCadastros();

    Void deletarCadastro(Long id);
}
