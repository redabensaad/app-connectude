package com.redabensaad.connectude.controllers.dtos.responses;

import com.redabensaad.connectude.datasource.entities.AlunoEntity;

import java.time.LocalDate;
import java.util.List;

public record ResponseTurmaDTO (
        Long id,
        String nome,
        LocalDate dataEntrada,
        Long idDocente,
        Long idCurso,
        List<AlunoEntity> alunos) {
}
