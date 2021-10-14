package pl.mrtk.bookingapp.reservation;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void createReservation(ReservationData reservationData) {
        reservationRepository.add(reservationData);
    }
}
