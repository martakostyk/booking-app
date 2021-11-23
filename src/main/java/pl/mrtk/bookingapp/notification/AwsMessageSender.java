package pl.mrtk.bookingapp.notification;

import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AwsMessageSender implements MessageSender {

    private static final Logger log = LoggerFactory.getLogger(AwsMessageSender.class);

    private final NotificationMessagingTemplate messagingTemplate;

    private static final String TOPIC_NAME = "booking-app-notification-topic.fifo";
    private static final String MESSAGE_GROUP_ID = "booking-app-notification";

    public AwsMessageSender(NotificationMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void send(String message) {
        Map<String, Object> headers = Map.of("message-group-id", MESSAGE_GROUP_ID);
        messagingTemplate.convertAndSend(TOPIC_NAME, message, headers);
        log.info("Message has been published to Amazon SNS topic [{}]", TOPIC_NAME);
    }
}
