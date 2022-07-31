package com.subscriptionservice.service;

import com.subscriptionservice.SubscriptionServiceApplication;
import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.service.impl.EmailServiceImpl;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SubscriptionServiceApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class EmailServiceCircuitBreakerFunctionalTest {

    public static final float FAILURE_RATE_THRESHOLD_EXPECTED = 50.0f;
    public static final int SLIDING_WINDOW_SIZE_EXPECTED = 5;

    @Value("${email}")
    private String email;

    @Value("${firstName}")
    private String firstName;

    @Value("${gender}")
    private String gender;

    @Value("${consent}")
    private boolean consent;

    @Value("${newsletterId}")
    private long newsletterId;

    @Value("${message}")
    private String message;

    @Value("${subject}")
    private String subject;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    CircuitBreakerProperties circuitBreakerProperties;


    @Test
    public void sendEmailIsExecutedWhileCircuitBreakerIsInClosedState() {
        //given
        this.circuitBreakerRegistry.circuitBreaker(EmailServiceImpl.SEND_EMAIL_CIRCUIT_BREAKER).transitionToClosedState();
        final SubscriptionRqDTO request = createSubscriptionRequest();

        //when
        this.emailService.sendEmail(request);

        //then
        assertThat(this.circuitBreakerRegistry).isNotNull();
        assertThat(this.circuitBreakerProperties).isNotNull();

        final CircuitBreaker circuitBreaker = this.circuitBreakerRegistry.circuitBreaker(EmailServiceImpl.SEND_EMAIL_CIRCUIT_BREAKER);

        assertThat(circuitBreaker.getCircuitBreakerConfig().getFailureRateThreshold()).isEqualTo(FAILURE_RATE_THRESHOLD_EXPECTED);
        assertThat(circuitBreaker.getCircuitBreakerConfig().getSlidingWindowSize()).isEqualTo(SLIDING_WINDOW_SIZE_EXPECTED);
        //when the circuit breaker is in CLOSED state, the microservice is being called
        //assertThat(circuitBreaker.getMetrics().getNumberOfSuccessfulCalls()).isEqualTo(1);
        //assertThat(circuitBreaker.getMetrics().getNumberOfFailedCalls()).isEqualTo(0);
     }

    @Test
    public void sendEmailIsExecutedWhileCircuitBreakerIsInOpenState() {
        //given
        this.circuitBreakerRegistry.circuitBreaker(EmailServiceImpl.SEND_EMAIL_CIRCUIT_BREAKER).transitionToOpenState();
        final SubscriptionRqDTO request = createSubscriptionRequest();

        //when
        this.emailService.sendEmail(request);

        //then
        assertThat(this.circuitBreakerRegistry).isNotNull();
        assertThat(this.circuitBreakerProperties).isNotNull();

        final CircuitBreaker circuitBreaker = this.circuitBreakerRegistry.circuitBreaker(EmailServiceImpl.SEND_EMAIL_CIRCUIT_BREAKER);

        assertThat(circuitBreaker.getCircuitBreakerConfig().getFailureRateThreshold()).isEqualTo(FAILURE_RATE_THRESHOLD_EXPECTED);
        assertThat(circuitBreaker.getCircuitBreakerConfig().getSlidingWindowSize()).isEqualTo(SLIDING_WINDOW_SIZE_EXPECTED);
        //when the circuit breaker is in OPEN state, the microservice is not called and the fallback method is executed
        //assertThat(circuitBreaker.getMetrics().getNumberOfFailedCalls()).isEqualTo(0);
        //assertThat(circuitBreaker.getMetrics().getNumberOfSuccessfulCalls()).isEqualTo(0);
    }


    private SubscriptionRqDTO createSubscriptionRequest() {
        return SubscriptionRqDTO.builder()
                .email(this.email)
                .consent(this.consent)
                .birthDate(new LocalDate().toDate())
                .firstName(this.firstName)
                .gender(this.gender)
                .newsletterId(this.newsletterId)
                .build();
    }

}
