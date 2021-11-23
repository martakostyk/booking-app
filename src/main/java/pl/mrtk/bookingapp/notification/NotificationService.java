package pl.mrtk.bookingapp.notification;

import org.springframework.stereotype.Service;
import pl.mrtk.bookingapp.reservation.ReservationData;

@Service
public class NotificationService {

    private final MessageSender messageSender;

    public NotificationService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendNotification(ReservationData reservationData) {
        messageSender.send(String.format("reservation notification: {date: %s, name: %s}",
                reservationData.getDateTime(), reservationData.getName()));
    }
}
