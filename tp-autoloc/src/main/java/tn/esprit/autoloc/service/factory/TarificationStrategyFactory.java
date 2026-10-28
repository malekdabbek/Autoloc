package tn.esprit.autoloc.service.factory;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.strategy.ITarificationStrategy;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TarificationStrategyFactory {

    private final Map<String, ITarificationStrategy> strategies;

    public TarificationStrategyFactory(List<ITarificationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        ITarificationStrategy::getCode,
                        Function.identity()
                ));
    }

    public ITarificationStrategy resolve(Reservation reservation) {
        long jours = ChronoUnit.DAYS.between(
                reservation.getDateDebut(),
                reservation.getDateFin()
        );

        if (jours >= 7) {
            return strategies.get("LONGUE_DUREE");
        }

        if (jours <= 3 && estWeekend(reservation)) {
            return strategies.get("WEEKEND");
        }

        return strategies.get("STANDARD");
    }

    private boolean estWeekend(Reservation reservation) {
        int jourDebut = reservation.getDateDebut()
                .getDayOfWeek()
                .getValue();

        return jourDebut >= 5; // vendredi, samedi, dimanche
    }
}
