package com.redabensaad.connectude.datasource.repositories;

import com.redabensaad.connectude.datasource.entities.PapelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PapelRepository extends JpaRepository<PapelEntity, Long> {
}
