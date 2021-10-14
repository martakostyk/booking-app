package pl.mrtk.bookingapp.reservation;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public interface Reservation {

    long getId();
    String getName();
    LocalDateTime getDateTime();
}
