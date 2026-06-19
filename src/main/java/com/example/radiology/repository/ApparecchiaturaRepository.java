package com.example.radiology.repository;

import org.springframework.data.jpa.repository.*;
import com.example.radiology.entity.*;

public interface ApparecchiaturaRepository extends JpaRepository<Apparecchiatura, Long> {
}