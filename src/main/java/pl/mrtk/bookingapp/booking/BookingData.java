package pl.mrtk.bookingapp.booking;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableBookingData.class)
public interface BookingData {

    String getName();
    String getDateTime();
}
