package com.benjamin.parsy.ksb.user.usecase;

import com.benjamin.parsy.ksb.user.entity.event.UserEventPublisher;
import com.benjamin.parsy.ksb.user.entity.exception.UserNotFoundException;
import com.benjamin.parsy.ksb.user.entity.gateway.EventGateway;
import com.benjamin.parsy.ksb.user.entity.gateway.UserGateway;
import com.benjamin.parsy.ksb.user.entity.model.User;
import com.benjamin.parsy.ksb.user.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.user.entity.model.event.UserValidatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ValidatedUserUseCaseTest {

    @InjectMocks
    private ValidatedUserUseCase validatedUserUseCase;

    @Mock
    private UserGateway userGateway;

    @Mock
    private EventGateway eventGateway;

    @Mock
    private UserEventPublisher userEventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateUser_UserPresent_PublishUserValidated() throws UserNotFoundException {

        // Given
        UUID orderUuid = UUID.randomUUID();
        User user = DataTestUtils.createUser();

        when(userGateway.findById(user.getUuid()))
                .thenReturn(user);

        // When
        validatedUserUseCase.validateUser(orderUuid, user.getUuid());

        // Then
        verify(userGateway, times(1)).findById(any(UUID.class));

        ArgumentCaptor<UserValidatedEvent> eventCaptor = ArgumentCaptor.forClass(UserValidatedEvent.class);

        verify(eventGateway, times(1)).save(any(UserValidatedEvent.class));
        verify(eventGateway).save(eventCaptor.capture());
        assertEquals("USER_VALIDATED", eventCaptor.getValue().getType());

        verify(userEventPublisher, times(1)).publishUserValidated(any(UserValidatedEvent.class));
        verify(userEventPublisher).publishUserValidated(eventCaptor.capture());
        assertEquals("USER_VALIDATED", eventCaptor.getValue().getType());

    }

    @Test
    void validateUser_UserNotPresent_PublishOrderFailed() throws UserNotFoundException {

        // Given
        UUID orderUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();

        when(userGateway.findById(userUuid))
                .thenThrow(new UserNotFoundException("User not found for id " + userUuid));

        // When
        validatedUserUseCase.validateUser(orderUuid, userUuid);

        // Then
        verify(userGateway, times(1)).findById(any(UUID.class));

        ArgumentCaptor<OrderFailedEvent> eventCaptor = ArgumentCaptor.forClass(OrderFailedEvent.class);

        verify(eventGateway, times(1)).save(any(OrderFailedEvent.class));
        verify(eventGateway).save(eventCaptor.capture());
        assertEquals("ORDER_FAILED", eventCaptor.getValue().getType());

        verify(userEventPublisher, times(1)).publishOrderFailed(any(OrderFailedEvent.class));
        verify(userEventPublisher).publishOrderFailed(eventCaptor.capture());
        assertEquals("ORDER_FAILED", eventCaptor.getValue().getType());

    }

}