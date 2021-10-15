package pl.mrtk.bookingapp.reservation;

import java.util.Set;

public interface ReservationRepository {

    void add(ReservationData reservationData);

    Set<ReservationDto> getAll();
}
