package com.redabensaad.connectude.datasource.repositories;

import com.redabensaad.connectude.datasource.entities.TurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {

    List<TurmaEntity> findAllByIdCurso(Long idCurso);
    List<TurmaEntity> findAllByIdDocente(Long idDocente);
}
