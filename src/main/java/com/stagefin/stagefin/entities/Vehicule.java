package com.stagefin.stagefin.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String marque;
    private String modele;
    private Date date_fabrication;
    private String carburant;

    @OneToMany(mappedBy = "vehicule", orphanRemoval = true,cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    private Collection<Voyage> Voyage = new ArrayList<>();



}
