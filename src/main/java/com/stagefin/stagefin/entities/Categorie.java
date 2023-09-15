package com.stagefin.stagefin.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double prix;

    @OneToMany(mappedBy = "categorie", orphanRemoval = true,cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    private Collection<Voyage> voyage = new ArrayList<>();



    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }


}
