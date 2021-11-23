package pl.mrtk.bookingapp.reservation;

import org.springframework.stereotype.Service;
import pl.mrtk.bookingapp.db.ReservationRepository;

import java.util.Set;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void createReservation(ReservationData reservationData) {
        reservationRepository.add(reservationData);
    }

    public Set<ReservationDto> getReservations() {
        return reservationRepository.getAll();
    }
}
