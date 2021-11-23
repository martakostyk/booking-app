package pl.mrtk.bookingapp.notification;

import org.springframework.stereotype.Service;

@Service
public class BookingNotificationService {

    private final MessageSender messageSender;

    public BookingNotificationService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendNotification(BookingNotification notification) {
        messageSender.send(notification.toString()); // TODO better format of notification
    }
}
