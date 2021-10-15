package pl.mrtk.bookingapp.reservation;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public interface ReservationDto {

    String getName();
    LocalDateTime getDateTime();
    LocalDateTime getCreationTimestamp();

}
