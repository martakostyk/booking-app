package pl.mrtk.bookingapp.db;


import org.springframework.stereotype.Repository;
import pl.mrtk.bookingapp.reservation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong availableId = new AtomicLong(0L);

    @Override
    public void add(ReservationData reservationData) {
        ImmutableReservation reservation = ImmutableReservation.builder()
                .name(reservationData.getName())
                .dateTime(LocalDateTime.parse(reservationData.getDateTime()))
                .creationTimestamp(LocalDateTime.now())
                .id(availableId.getAndIncrement())
                .build();
        reservations.put(reservation.getId(), reservation);
    }

    @Override
    public Set<ReservationDto> getAll() {
        return reservations.values().stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }

    private ReservationDto convert(Reservation reservation) {
        return ImmutableReservationDto.builder()
                .name(reservation.getName())
                .dateTime(reservation.getDateTime())
                .creationTimestamp(reservation.getCreationTimestamp())
                .build();
    }
}
