package com.benjamin.parsy.runnetic.user.usecase;

import com.benjamin.parsy.runnetic.user.usecase.exception.UserNotFoundException;
import com.benjamin.parsy.runnetic.user.usecase.port.EventPort;
import com.benjamin.parsy.runnetic.user.usecase.port.UserPort;
import com.benjamin.parsy.runnetic.user.entity.model.User;
import com.benjamin.parsy.runnetic.user.entity.model.event.EventType;
import com.benjamin.parsy.runnetic.user.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.runnetic.user.entity.model.event.UserValidatedEvent;
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
    private UserPort userPort;

    @Mock
    private EventPort eventPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateUser_UserPresent_PublishUserValidated() throws UserNotFoundException {

        // Given
        UUID orderUuid = UUID.randomUUID();
        User user = DataTestUtils.createUser();

        doNothing().when(userPort)
                .existsById(user.getUuid());

        // When
        validatedUserUseCase.validateUser(orderUuid, user.getUuid());

        // Then
        verify(userPort, times(1)).existsById(any(UUID.class));

        ArgumentCaptor<UserValidatedEvent> eventCaptor = ArgumentCaptor.forClass(UserValidatedEvent.class);
        verify(eventPort, times(1)).publish(eventCaptor.capture());
        assertEquals(EventType.USER_VALIDATED, eventCaptor.getValue().getEventType());

    }

    @Test
    void validateUser_UserNotPresent_PublishOrderFailed() throws UserNotFoundException {

        // Given
        UUID orderUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();

        doThrow(new UserNotFoundException("User not found for id " + userUuid))
                .when(userPort)
                .existsById(userUuid);

        // When
        validatedUserUseCase.validateUser(orderUuid, userUuid);

        // Then
        verify(userPort, times(1)).existsById(any(UUID.class));

        ArgumentCaptor<OrderFailedEvent> eventCaptor = ArgumentCaptor.forClass(OrderFailedEvent.class);
        verify(eventPort, times(1)).publish(eventCaptor.capture());
        assertEquals(EventType.ORDER_FAILED, eventCaptor.getValue().getEventType());

    }

}