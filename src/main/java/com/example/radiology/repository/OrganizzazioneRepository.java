package com.example.radiology.repository;

import com.example.radiology.entity.Organizzazione;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizzazioneRepository extends JpaRepository<Organizzazione, Long> {

    @EntityGraph(attributePaths = {"apparecchiatureDirette", "contenitori"})
    Optional<Organizzazione> findById(Long id);
}
