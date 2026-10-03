package com.redabensaad.connectude.interfaces;

import com.redabensaad.connectude.controllers.dtos.requests.RequestTurmaDTO;
import com.redabensaad.connectude.controllers.dtos.responses.ResponseTurmaDTO;
import com.redabensaad.connectude.datasource.entities.TurmaEntity;

import java.util.List;

public interface TurmaInterface {
    TurmaEntity criarTurma(RequestTurmaDTO requestTurmaDTO);

    TurmaEntity criarTurmaEntity(RequestTurmaDTO requestTurmaDTO);

    ResponseTurmaDTO criarResponseTurmaDTO(TurmaEntity turmaEntitySalvo);

    List<ResponseTurmaDTO> criarResponseTurmaDTO(List<TurmaEntity> turmaEntityList);

    List<TurmaEntity> listarTurmas();

    TurmaEntity buscarTurmaPorId(Long id);

    TurmaEntity atualizarTurma(Long id, RequestTurmaDTO requestTurmaDTO);

    Void deletarTurma(Long id);
}
