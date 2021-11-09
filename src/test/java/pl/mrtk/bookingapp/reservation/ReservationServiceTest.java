package pl.mrtk.bookingapp.reservation;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReservationServiceTest {

    private final ReservationRepository repository = Mockito.mock(ReservationRepository.class);
    private final ReservationService service = new ReservationService(repository);

    @Test
    public void testCreateReservation() {
        // given
        ReservationData reservationData = Mockito.mock(ReservationData.class);

        // when
        service.createReservation(reservationData);

        // then
        Mockito.verify(repository, Mockito.times(2)).add(reservationData);
    }
}