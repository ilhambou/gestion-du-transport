package com.stagefin.stagefin.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
    @Min(value = 1, message = "Poids must be greater than zero")

    private double poids;


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(updatable = false)

    private Client client;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "voyage_id")
    private Voyage voyage;

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", poids=" + poids +
                '}';
    }

}
