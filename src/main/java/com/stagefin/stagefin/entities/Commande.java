package com.stagefin.stagefin.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double poids;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(updatable = false)

    private Client client;

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", poids=" + poids +
                '}';
    }

}
