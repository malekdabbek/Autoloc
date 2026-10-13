package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.repository.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class TpAutolocApplicationTests {
@Autowired
private IAgenceRepository agenceRepository;
@Autowired
private IClientRepository clientRepository;
@Autowired
private IVehiculeRepository vehiculeRepository;
@Autowired
private IEmoloyeRepository employeRepository;
@Autowired
private IEquipementRepository equipementRepository;
@Autowired
private IMaintenanceRepository maintenanceRepository;
    @Test
    void testAgenceRepository() {
        System.out.println("********** Agence **********");
        long countAvant = agenceRepository.count();
        //creation d'une instance agence
        Agence agence = new Agence();
        agence.setNom("Agence1");
        agence.setAdresse("Tunis");
        Agence agencesaved=agenceRepository.save(agence);
        System.out.println("agence cree: "+agencesaved);
        //findAll
        List<Agence> agences=agenceRepository.findAll();
        System.out.println("Liste des agences: "+agences);
        System.out.println("Nombre d'agences: "+agences.size());
        assertNotNull(agences);
        assertFalse(agences.isEmpty(),"La base doit contenir au moins une agence");
        // findById
        Optional<Agence> found = agenceRepository.findById(agencesaved.getIdAgence());
        System.out.println("Agence trouvée : " + found);
        assertTrue(found.isPresent());
        assertEquals("Agence1", found.get().getNom());

        // existsById
        assertTrue(agenceRepository.existsById(agencesaved.getIdAgence()));

        // count
        System.out.println("Nombre d'agences : " + agenceRepository.count());
        assertEquals(countAvant+ 1, agenceRepository.count());

        // deleteById
        agenceRepository.deleteById(agencesaved.getIdAgence());
        assertFalse(agenceRepository.existsById(agencesaved.getIdAgence()));
        assertEquals(agenceRepository.count(), agenceRepository.count());
        System.out.println("Agence supprimée, existe encore ? "
                + agenceRepository.existsById(agencesaved.getIdAgence()));
    }

    @Test
    void testClientRepository() {
        System.out.println("********** Client **********");
        long countAvant = clientRepository.count();

        // ADAPTER les setters aux attributs réels de ton entité Client
        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Ahmed");

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sara");

        // save x2
        Client s1 = clientRepository.save(c1);
        Client s2 = clientRepository.save(c2);
        System.out.println("Client 1 : " + s1);
        System.out.println("Client 2 : " + s2);
        assertNotNull(s1.getIdClient());
        assertNotNull(s2.getIdClient());
        assertNotEquals(s1.getIdClient(), s2.getIdClient());

        // findAll
        List<Client> clients = clientRepository.findAll();
        System.out.println("Liste des clients : " + clients);
        assertTrue(clients.size() >= 2);

        // findById
        Optional<Client> found = clientRepository.findById(s1.getIdClient());
        System.out.println("Client trouvé : " + found);
        assertTrue(found.isPresent());

        // existsById : id existant puis inexistant
        assertTrue(clientRepository.existsById(s1.getIdClient()));
        assertFalse(clientRepository.existsById(999999L));
        System.out.println("existsById(existant) = " + clientRepository.existsById(s1.getIdClient()));
        System.out.println("existsById(999999)   = " + clientRepository.existsById(999999L));

        // count
        System.out.println("Nombre de clients : " + clientRepository.count());
        assertEquals(countAvant + 2, clientRepository.count());

        // nettoyage
        clientRepository.deleteById(s1.getIdClient());
        clientRepository.deleteById(s2.getIdClient());
        assertEquals(countAvant, clientRepository.count());
    }
    @Test
    void testVehiculeRepository() {
        System.out.println("********** Vehicule **********");
        long countAvant = vehiculeRepository.count();

        // ADAPTER les setters aux attributs réels de ton entité Vehicule
        Vehicule v = new Vehicule();
        v.setMarque("Peugeot");
        v.setModele("208");

        // save
        Vehicule saved = vehiculeRepository.save(v);
        System.out.println("Véhicule créé : " + saved);
        assertNotNull(saved.getIdVehicule());

        // findAll
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        System.out.println("Liste des véhicules : " + vehicules);
        assertFalse(vehicules.isEmpty());

        // count
        System.out.println("Nombre de véhicules : " + vehiculeRepository.count());
        assertEquals(countAvant + 1, vehiculeRepository.count());

        // modification : id renseigné -> merge (SELECT puis UPDATE dans les logs)
        saved.setModele("308");
        Vehicule updated = vehiculeRepository.save(saved);
        System.out.println("Véhicule modifié : " + updated);
        assertEquals("308", updated.getModele());
        assertEquals(saved.getIdVehicule(), updated.getIdVehicule());
        // pas de nouvelle ligne créée
        assertEquals(countAvant + 1, vehiculeRepository.count());

        // deleteById
        vehiculeRepository.deleteById(updated.getIdVehicule());
        assertFalse(vehiculeRepository.existsById(updated.getIdVehicule()));
        System.out.println("Véhicule supprimé.");
    }
    @Test
    void testEmployeRepository() {
        System.out.println("********** Employe **********");

        // ADAPTER : si Employe a une Agence obligatoire, la créer et l'affecter d'abord
        Employe e = new Employe();
        e.setNom("Mansouri");
        e.setPrenom("Karim");

        Employe saved = employeRepository.save(e);
        System.out.println("Employé créé : " + saved);
        assertNotNull(saved.getIdEmploye());

        assertTrue(employeRepository.findById(saved.getIdEmploye()).isPresent());
        System.out.println("Tous les employés : " + employeRepository.findAll());
        assertTrue(employeRepository.existsById(saved.getIdEmploye()));

        employeRepository.deleteById(saved.getIdEmploye());
        assertFalse(employeRepository.existsById(saved.getIdEmploye()));
        System.out.println("Employé supprimé.");
    }

    @Test
    void testEquipementRepository() {
        System.out.println("********** Equipement **********");

        // ADAPTER aux attributs réels de Equipement
        Equipement eq = new Equipement();
        eq.setLibelle("GPS");

        Equipement saved = equipementRepository.save(eq);
        System.out.println("Équipement créé : " + saved);
        assertNotNull(saved.getIdEquipement());

        assertTrue(equipementRepository.findById(saved.getIdEquipement()).isPresent());
        System.out.println("Tous les équipements : " + equipementRepository.findAll());
        assertTrue(equipementRepository.existsById(saved.getIdEquipement()));

        equipementRepository.deleteById(saved.getIdEquipement());
        assertFalse(equipementRepository.existsById(saved.getIdEquipement()));
        System.out.println("Équipement supprimé.");
    }


    @Test
    void testMaintenanceRepository() {
        System.out.println("********** Maintenance **********");

        // Le parent doit exister avant la maintenance (clé étrangère)
        Vehicule v = new Vehicule();
        v.setMarque("Renault");
        v.setModele("Clio");
        Vehicule vSaved = vehiculeRepository.save(v);

        // ADAPTER aux attributs réels de Maintenance
        Maintenance m = new Maintenance();
        m.setVehicule(vSaved);
        // m.setDescription("Vidange"); m.setDate(LocalDate.now()); ...

        Maintenance saved = maintenanceRepository.save(m);
        System.out.println("Maintenance créée : " + saved);
        assertNotNull(saved.getIdMaintenance());

        assertTrue(maintenanceRepository.findById(saved.getIdMaintenance()).isPresent());
        System.out.println("Toutes les maintenances : " + maintenanceRepository.findAll());
        assertTrue(maintenanceRepository.existsById(saved.getIdMaintenance()));

        // supprimer l'enfant avant le parent
        maintenanceRepository.deleteById(saved.getIdMaintenance());
        assertFalse(maintenanceRepository.existsById(saved.getIdMaintenance()));
        vehiculeRepository.deleteById(vSaved.getIdVehicule());
        System.out.println("Maintenance et véhicule supprimés.");
    }

}
