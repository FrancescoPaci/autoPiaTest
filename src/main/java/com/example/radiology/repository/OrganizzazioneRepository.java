package com.example.radiology.repository;

import com.example.radiology.entity.Organizzazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizzazioneRepository extends JpaRepository<Organizzazione, Long> {

}
