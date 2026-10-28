package tn.esprit.autoloc.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.enums.StatutReservation;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;
    @ManyToOne
    @JsonBackReference("client-reservations")
    Client client;
    @ManyToOne
    @JsonBackReference("vehicule-reservations")
    Vehicule vehicule;
    @OneToOne
    private Contrat contrat;
}
