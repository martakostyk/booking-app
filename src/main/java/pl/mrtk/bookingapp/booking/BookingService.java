package pl.mrtk.bookingapp.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.mrtk.bookingapp.db.BookingRepository;

import java.util.Set;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void book(BookingData bookingData) {
        bookingRepository.add(bookingData);
        log.info("Booking created successfully, date: '{}', name: '{}'", bookingData.getDateTime(), bookingData.getName());
    }

    public Set<BookingDto> getBookings() {
        return bookingRepository.getAll();
    }
}
