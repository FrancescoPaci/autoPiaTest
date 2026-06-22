package com.example.radiology.repository;

import com.example.radiology.entity.Apparecchiatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApparecchiaturaRepository extends JpaRepository<Apparecchiatura, Long> {
}