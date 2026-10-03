package com.redabensaad.connectude.controllers.dtos.responses;

import java.time.LocalDate;

public record ResponseMateriaDTO(
        Long id,
        String nome,
        LocalDate dataEntrada,
        Long idCurso) {
}
