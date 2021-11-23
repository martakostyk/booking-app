package pl.mrtk.bookingapp.notification;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AwsMessagingConfiguration {

    private static final String ACCESS_KEY = "default";
    private static final String SECRET_KEY = "default";
    private static final String URL = "http://localhost:4566";
    private static final String REGION = "eu-west-2";

    @Bean
    public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNSAsync amazonSNSAsync) {
        return new NotificationMessagingTemplate(amazonSNSAsync);
    }

    @Bean
    @Primary
    public AmazonSNSAsync amazonSnsAsync(BasicAWSCredentials credentials,
                                         AwsClientBuilder.EndpointConfiguration endpointConfiguration) {
        return AmazonSNSAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    @Bean
    BasicAWSCredentials credentials() {
        return new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
    }

    @Bean
    AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(URL, REGION);
    }
}
