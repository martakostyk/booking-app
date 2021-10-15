package pl.mrtk.bookingapp.reservation;

import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public void createReservation(@RequestBody ReservationData reservationData) {
        reservationService.createReservation(reservationData);
    }

    @GetMapping
    public Set<ReservationDto> getReservations() {
        return reservationService.getReservations();
    }
}
