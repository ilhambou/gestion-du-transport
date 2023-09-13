package com.stagefin.stagefin.repository;

import com.stagefin.stagefin.entities.Vehicule;
import com.stagefin.stagefin.entities.Voyage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    Page<Voyage> findByNomContains(String kw, Pageable pageable);

}
