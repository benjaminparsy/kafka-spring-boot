package com.benjamin.parsy.ksb.user.usecase;

import com.benjamin.parsy.ksb.user.entity.event.EventPublisher;
import com.benjamin.parsy.ksb.user.entity.exception.UserNotFoundException;
import com.benjamin.parsy.ksb.user.entity.gateway.EventGateway;
import com.benjamin.parsy.ksb.user.entity.gateway.UserGateway;
import com.benjamin.parsy.ksb.user.entity.model.User;
import com.benjamin.parsy.ksb.user.entity.model.event.EventType;
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
    private EventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateUser_UserPresent_PublishUserValidated() throws UserNotFoundException {

        // Given
        UUID orderUuid = UUID.randomUUID();
        User user = DataTestUtils.createUser();

        doNothing().when(userGateway)
                .existsById(user.getUuid());

        // When
        validatedUserUseCase.validateUser(orderUuid, user.getUuid());

        // Then
        verify(userGateway, times(1)).existsById(any(UUID.class));

        ArgumentCaptor<UserValidatedEvent> eventCaptor = ArgumentCaptor.forClass(UserValidatedEvent.class);

        verify(eventGateway, times(1)).save(any(UserValidatedEvent.class));
        verify(eventGateway).save(eventCaptor.capture());
        assertEquals(EventType.USER_VALIDATED, eventCaptor.getValue().getEventType());

        verify(eventPublisher, times(1)).publishUserValidatedEvent(any(UserValidatedEvent.class));
        verify(eventPublisher).publishUserValidatedEvent(eventCaptor.capture());
        assertEquals(EventType.USER_VALIDATED, eventCaptor.getValue().getEventType());

    }

    @Test
    void validateUser_UserNotPresent_PublishOrderFailed() throws UserNotFoundException {

        // Given
        UUID orderUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();

        doThrow(new UserNotFoundException("User not found for id " + userUuid))
                .when(userGateway)
                .existsById(userUuid);

        // When
        validatedUserUseCase.validateUser(orderUuid, userUuid);

        // Then
        verify(userGateway, times(1)).existsById(any(UUID.class));

        ArgumentCaptor<OrderFailedEvent> eventCaptor = ArgumentCaptor.forClass(OrderFailedEvent.class);

        verify(eventGateway, times(1)).save(any(OrderFailedEvent.class));
        verify(eventGateway).save(eventCaptor.capture());
        assertEquals(EventType.ORDER_FAILED, eventCaptor.getValue().getEventType());

        verify(eventPublisher, times(1)).publishOrderFailedEvent(any(OrderFailedEvent.class));
        verify(eventPublisher).publishOrderFailedEvent(eventCaptor.capture());
        assertEquals(EventType.ORDER_FAILED, eventCaptor.getValue().getEventType());

    }

}