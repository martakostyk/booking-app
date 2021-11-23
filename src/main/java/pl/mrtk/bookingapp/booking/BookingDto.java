package pl.mrtk.bookingapp.booking;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public interface BookingDto {

    String getName();
    LocalDateTime getDateTime();
    LocalDateTime getCreationTimestamp();

}
