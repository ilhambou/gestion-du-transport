package com.stagefin.stagefin.repository;

import com.stagefin.stagefin.entities.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande,Long> {
    Page<Commande> findByNomContains(String kw, Pageable pageable);


}
