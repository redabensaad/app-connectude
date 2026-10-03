package com.redabensaad.connectude.controllers.dtos.responses;

import com.redabensaad.connectude.datasource.entities.MateriaEntity;

import java.util.List;

public record ResponseCursoMateriasDTO (
    Long id,
    List<MateriaEntity> materias) {
}
