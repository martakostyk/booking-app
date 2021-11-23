package pl.mrtk.bookingapp.booking;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.mrtk.bookingapp.db.BookingRepository;

class BookingServiceTest {

    private final BookingRepository repository = Mockito.mock(BookingRepository.class);
    private final BookingService service = new BookingService(repository);

    @Test
    public void testBook() {
        // given
        BookingData bookingData = Mockito.mock(BookingData.class);

        // when
        service.book(bookingData);

        // then
        Mockito.verify(repository, Mockito.times(1)).add(bookingData);
    }
}
