package com.stagefin.stagefin.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 4, max = 20)
    private String nom;
    @NotEmpty @Size(min = 4, max = 20)
    private String prenom;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    @Column(unique = true)
    @Email(message = "Adresse email invalide")
    @NotEmpty
    private String email;
    @NotEmpty
    private String sexe;
    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be a 10-digit number")
    private String tel;
    @NotEmpty
    private String CIN;

    @OneToMany(mappedBy = "client", orphanRemoval = true,cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    private Collection<Commande> Commande = new ArrayList<>();

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }



}
