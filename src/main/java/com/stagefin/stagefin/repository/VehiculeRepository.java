package com.stagefin.stagefin.repository;

import com.stagefin.stagefin.entities.Vehicule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    Page<Vehicule> findByNomContains(String kw, Pageable pageable);

}
