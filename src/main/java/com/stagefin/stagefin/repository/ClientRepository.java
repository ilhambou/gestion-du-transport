package com.stagefin.stagefin.repository;

import com.stagefin.stagefin.entities.Client;
import com.stagefin.stagefin.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findByNomContains(String kw, Pageable pageable);
}
