package com.redabensaad.connectude.datasource.repositories;

import com.redabensaad.connectude.datasource.entities.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
    List<MateriaEntity> findAllByIdCurso(Long idCurso);
}
