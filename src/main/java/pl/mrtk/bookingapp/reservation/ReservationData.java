package pl.mrtk.bookingapp.reservation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableReservationData.class)
public interface ReservationData {

    String getName();
    String getDateTime();
}
