package com.stagefin.stagefin.repository;

import com.stagefin.stagefin.entities.Client;
import com.stagefin.stagefin.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findByNomContains(String kw, Pageable pageable);
    long countBySexe(String sexe);

    @Query("SELECT COUNT(c) FROM Client c WHERE YEAR(:currentDate) - YEAR(c.dateNaissance) > 25")
    long countClientsOlderThan25(@Param("currentDate") Date currentDate);

    @Query("SELECT COUNT(c) FROM Client c WHERE YEAR(:currentDate) - YEAR(c.dateNaissance) < 25")
    long countClientsYoungerThan25(@Param("currentDate") Date currentDate);
}


