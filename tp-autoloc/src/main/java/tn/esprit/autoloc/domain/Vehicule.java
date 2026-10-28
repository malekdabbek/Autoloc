package tn.esprit.autoloc.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.enums.CategorieVehicule;
import tn.esprit.autoloc.domain.enums.StatutVehicule;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;
    private String immatriculation;
    private String marque;
    private String modele;
    private CategorieVehicule categorie;
    private BigDecimal tarifJournalier;
    private StatutVehicule statut;

    @ManyToOne
    @JsonBackReference("agence-vehicules")
    Agence agence;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicule")
    @JsonManagedReference("vehicule-reservations")
    private Set<Reservation> reservations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicule")
    @JsonManagedReference("vehicule-maintenances")
    private Set<Maintenance> maintenances;

    @ManyToMany(mappedBy = "vehicules", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Equipement> equipements;
}