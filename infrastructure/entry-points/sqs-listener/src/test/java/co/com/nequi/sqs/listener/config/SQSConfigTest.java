package co.com.nequi.sqs.listener.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.metrics.LoggingMetricPublisher;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SQSConfigTest {

    @Mock
    private SqsAsyncClient asyncClient;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void configTest() {
        SQSProperties sqsProperties = new SQSProperties();
        sqsProperties.setNumberOfThreads(1);
        sqsProperties.setRegion("Region");
        LoggingMetricPublisher loggingMetricPublisher = LoggingMetricPublisher.create();
        SQSConfig sqsConfig = new SQSConfig();

        assertNotNull(sqsConfig.sqsListener(asyncClient, sqsProperties, message -> Mono.empty()));
        assertNotNull(sqsConfig.configSqs(sqsProperties, loggingMetricPublisher));

    }
}