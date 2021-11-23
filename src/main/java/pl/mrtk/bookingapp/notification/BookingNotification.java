package pl.mrtk.bookingapp.notification;

import org.immutables.value.Value;

@Value.Immutable
public interface BookingNotification {

    String getName();
    String getDateTime();
    BookingStatus getBookingStatus();
    String message();
}
