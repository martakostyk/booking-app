package pl.mrtk.bookingapp.db;

import pl.mrtk.bookingapp.reservation.ReservationData;
import pl.mrtk.bookingapp.reservation.ReservationDto;

import java.util.Set;

public interface ReservationRepository {

    void add(ReservationData reservationData);

    Set<ReservationDto> getAll();
}
