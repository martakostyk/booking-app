package pl.mrtk.bookingapp.db;

import pl.mrtk.bookingapp.booking.BookingData;
import pl.mrtk.bookingapp.booking.BookingDto;

import java.util.Set;

public interface BookingRepository {

    void add(BookingData bookingData);

    Set<BookingDto> getAll();
}
