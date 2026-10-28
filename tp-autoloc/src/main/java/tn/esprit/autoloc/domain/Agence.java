package tn.esprit.autoloc.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agence")
    @JsonManagedReference("agence-employes")
    private Set<Employe> employes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agence")
    @JsonManagedReference("agence-vehicules")
    private Set<Vehicule> vehicules;
}
