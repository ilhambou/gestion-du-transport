package com.stagefin.stagefin.repository;

import com.stagefin.stagefin.entities.Categorie;
import com.stagefin.stagefin.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository  extends JpaRepository<Categorie, Long> {
    Page<Categorie> findByNomContains(String kw, Pageable pageable);
}
