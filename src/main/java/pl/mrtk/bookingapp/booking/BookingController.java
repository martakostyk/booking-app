package pl.mrtk.bookingapp.booking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mrtk.bookingapp.notification.BookingNotification;
import pl.mrtk.bookingapp.notification.BookingNotificationService;
import pl.mrtk.bookingapp.notification.BookingStatus;
import pl.mrtk.bookingapp.notification.ImmutableBookingNotification;

import java.util.Set;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingNotificationService bookingNotificationService;

    public BookingController(BookingService bookingService, BookingNotificationService bookingNotificationService) {
        this.bookingService = bookingService;
        this.bookingNotificationService = bookingNotificationService;
    }

    @PostMapping
    public void book(@RequestBody BookingData bookingData) {
        bookingService.book(bookingData);
        var notification = createNotification(bookingData);
        bookingNotificationService.sendNotification(notification);
    }

    private BookingNotification createNotification(BookingData bookingData) {
        return ImmutableBookingNotification.builder()
                .dateTime(bookingData.getDateTime())
                .name(bookingData.getName())
                .bookingStatus(BookingStatus.CREATED)
                .message("booking created")
                .build();
    }

    @GetMapping
    public Set<BookingDto> getBookings() {
        return bookingService.getBookings();
    }
}
