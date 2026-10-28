package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.IReservationService;

import java.util.List;

@Tag(name = "Réservations", description = "Gestion des réservations")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final IReservationService reservationService;
    @Operation(summary = "Lister : reservations")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    @GetMapping
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }
    @Operation(summary = "Consulter la réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation trouvée"),
            @ApiResponse(responseCode = "404", description = "Reservation introuvable") })
    @GetMapping("/{id}")
    public Reservation findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return reservationService.findById(id);
    }
    @Operation(summary = "Créer la réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reservation créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }
    @Operation(summary = "Modifier la réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation modifiée"),
            @ApiResponse(responseCode = "404", description = "Reservation introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides") })
    @PutMapping("/{id}")
    public Reservation update(@Parameter(description = "Identifiant") @PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }
    @Operation(summary = "Supprimer la réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reservation supprimée"),
            @ApiResponse(responseCode = "404", description = "Reservation introuvable") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
