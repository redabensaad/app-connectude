package com.redabensaad.connectude.datasource.repositories;

import com.redabensaad.connectude.datasource.entities.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {
}
