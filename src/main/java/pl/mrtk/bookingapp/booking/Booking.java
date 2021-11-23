package pl.mrtk.bookingapp.booking;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public interface Booking {

    long getId();
    String getName();
    LocalDateTime getDateTime();
    LocalDateTime getCreationTimestamp();
}
