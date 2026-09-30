package tn.esprit.autoloc.domain;
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
    Agence agence;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "vehicule")
    private Set<Reservation> reservations;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "vehicule")
    private Set<Maintenance> maintenances;
    @ManyToMany(mappedBy = "vehicules",cascade = CascadeType.ALL)
    private Set<Equipement> equipements;

}
