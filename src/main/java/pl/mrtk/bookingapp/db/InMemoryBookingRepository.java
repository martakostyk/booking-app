package pl.mrtk.bookingapp.db;


import org.springframework.stereotype.Repository;
import pl.mrtk.bookingapp.booking.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryBookingRepository implements BookingRepository {

    private final Map<Long, Booking> bookings = new HashMap<>();
    private final AtomicLong availableId = new AtomicLong(0L);

    @Override
    public void add(BookingData bookingData) {
        ImmutableBooking booking = ImmutableBooking.builder()
                .name(bookingData.getName())
                .dateTime(LocalDateTime.parse(bookingData.getDateTime()))
                .creationTimestamp(LocalDateTime.now())
                .id(availableId.getAndIncrement())
                .build();
        bookings.put(booking.getId(), booking);
    }

    @Override
    public Set<BookingDto> getAll() {
        return bookings.values().stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }

    private BookingDto convert(Booking booking) {
        return ImmutableBookingDto.builder()
                .name(booking.getName())
                .dateTime(booking.getDateTime())
                .creationTimestamp(booking.getCreationTimestamp())
                .build();
    }
}
