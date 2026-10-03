package com.redabensaad.connectude.datasource.repositories;

import com.redabensaad.connectude.datasource.entities.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<NotaEntity, Long> {
    List<NotaEntity> findAllByIdAluno(Long idAluno);

    List<NotaEntity> findAllByIdDocente(Long idDocente);

    List<NotaEntity> findAllByIdMateria(Long idMateria);
}
