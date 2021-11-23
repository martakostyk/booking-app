package pl.mrtk.bookingapp.reservation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mrtk.bookingapp.notification.NotificationService;

import java.util.Set;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final ReservationService reservationService;
    private final NotificationService notificationService;

    public BookingController(ReservationService reservationService, NotificationService notificationService) {
        this.reservationService = reservationService;
        this.notificationService = notificationService;
    }

    @PostMapping
    public void book(@RequestBody ReservationData reservationData) {
        reservationService.createReservation(reservationData);
        notificationService.sendNotification(reservationData);
    }

    @GetMapping
    public Set<ReservationDto> getReservations() {
        return reservationService.getReservations();
    }
}
