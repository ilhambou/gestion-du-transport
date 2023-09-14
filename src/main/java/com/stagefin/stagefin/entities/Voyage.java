package com.stagefin.stagefin.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nom;

    @NotNull
    private Date heure_depart;

    @NotNull
    private Date heure_arrive;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    @OneToMany(mappedBy = "voyage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Commande> commandes;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(updatable = false)

    private Employee employee;

    @Override
    public String toString() {
        return "Voyage{" +
                "id=" + id +
                // Other fields here
                ", vehicule=" + (vehicule != null ? vehicule.getId() : "null") + // Avoid calling vehicule.toString() directly
                '}';}


}
