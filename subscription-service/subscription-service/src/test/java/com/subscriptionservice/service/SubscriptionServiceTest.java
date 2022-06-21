package com.subscriptionservice.service;

import com.subscriptionservice.converter.SubscriptionConverter;
import com.subscriptionservice.converter.SubscriptionRqConverter;
import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.dto.SubscriptionRsDTO;
import com.subscriptionservice.entity.Subscription;
import com.subscriptionservice.enums.GenderType;
import com.subscriptionservice.exception.RegisteredSubscriptionException;
import com.subscriptionservice.exception.SubscriptionNotFound;
import com.subscriptionservice.repository.SubscriptionRepository;
import com.subscriptionservice.service.impl.SubscriptionServiceImpl;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
public class SubscriptionServiceTest {

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

    @Value("${subscriptionId}")
    private long subscriptionId;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Mock
    private SubscriptionRqConverter subscriptionRqConverter;

    @Mock
    private SubscriptionRepository repository;

    @Mock
    private SubscriptionConverter subscriptionConverter;

    @Mock
    private EmailService emailService;

    @Before
    public void before(){
        this.subscriptionService = new SubscriptionServiceImpl(this.subscriptionRqConverter,
                this.repository, this.subscriptionConverter, this.emailService);
    }

    @Test
    public void whenCreateSubscriptionIsExecutedThenVerifyData() {
        //given
        final SubscriptionRqDTO request = createSubscriptionRequest();
        final Subscription subscription = createSubscription();
        SubscriptionRsDTO subscriptionResponse = createSubscriptionResponse();
        Mockito.when(this.repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(this.subscriptionRqConverter.convert(Mockito.any())).thenReturn(subscription);
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(subscription);
        Mockito.when(this.subscriptionConverter.convert(Mockito.any())).thenReturn(subscriptionResponse);

        //when
        final SubscriptionRsDTO result = this.subscriptionService.createSubscription(request);

        //then
        assertNotNull(result);
        assertNotNull(result.getBirthDate());
        assertNotNull(result.getId());
        assertEquals(request.getEmail(), result.getEmail());
        assertEquals(request.getBirthDate(), result.getBirthDate());
     }

    @Test(expected = RegisteredSubscriptionException.class)
    public void whenCreateSubscriptionIsWithAndTheEmailExistsThenThrowException() {
        //given
        final SubscriptionRqDTO request = createSubscriptionRequest();
        final Subscription subscription = createSubscription();
        Mockito.when(this.repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(subscription));

        //when
        this.subscriptionService.createSubscription(request);

    }

    @Test
    public void whenRetrieveSubscriptionByIdIsExecutedThenVerifyData() {
        //given
        final Subscription subscription = createSubscription();
        SubscriptionRsDTO subscriptionResponse = createSubscriptionResponse();
        Mockito.when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.of(subscription));
        Mockito.when(this.subscriptionConverter.convert(Mockito.any())).thenReturn(subscriptionResponse);

        //when
        final SubscriptionRsDTO result = this.subscriptionService.retrieveSubscription(subscriptionId);

        //then
        assertNotNull(result);
        assertNotNull(result.getBirthDate());
        assertNotNull(result.getId());
    }

    @Test(expected = SubscriptionNotFound.class)
    public void whenRetrieveSubscriptionByIdIsExecutedAndSubscriptionIsNotFoundThenThrowException() {
        //given
        Mockito.when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        //when
        this.subscriptionService.retrieveSubscription(subscriptionId);
    }

    @Test
    public void whenRetrieveSubscriptionsIsExecutedThenVerifyResult() {
        //given
        final List<Subscription> subscriptions = Arrays.asList(createSubscription());
        SubscriptionRsDTO subscriptionResponse = createSubscriptionResponse();
        Mockito.when(this.repository.findAll()).thenReturn(subscriptions);
        Mockito.when(this.subscriptionConverter.convert(Mockito.any())).thenReturn(subscriptionResponse);

        //when
        final List<SubscriptionRsDTO> result = this.subscriptionService.retrievesSubscription();

        //then
        assertNotNull(result);
        assertTrue(!result.isEmpty());
     }

    @Test
    public void whenRetrieveSubscriptionsIsExecutedThenVerifyEmptyList() {
        //given
        Mockito.when(this.repository.findAll()).thenReturn(new ArrayList<>());

        //when
        final List<SubscriptionRsDTO> result = this.subscriptionService.retrievesSubscription();

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
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

    private Subscription createSubscription() {
        return Subscription.builder()
                .id(subscriptionId)
                .email(this.email)
                .consent(this.consent)
                .birthDate(new LocalDate().toDate())
                .firstName(this.firstName)
                .gender(GenderType.resolveByGender(this.gender))
                .newsletterId(this.newsletterId)
                .build();
    }

    private SubscriptionRsDTO createSubscriptionResponse() {
        return SubscriptionRsDTO.builder()
                .id(this.subscriptionId)
                .email(this.email)
                .consent(this.consent)
                .birthDate(new LocalDate().toDate())
                .firstName(this.firstName)
                .gender(GenderType.resolveByGender(this.gender))
                .newsletterId(this.newsletterId)
                .build();
    }


}
