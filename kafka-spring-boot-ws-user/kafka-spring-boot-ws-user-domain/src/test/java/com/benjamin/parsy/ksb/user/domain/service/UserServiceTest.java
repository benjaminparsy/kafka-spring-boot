package com.benjamin.parsy.ksb.user.domain.service;

import com.benjamin.parsy.ksb.user.domain.DataTestUtils;
import com.benjamin.parsy.ksb.user.domain.model.User;
import com.benjamin.parsy.ksb.user.domain.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.user.domain.model.event.UserValidatedEvent;
import com.benjamin.parsy.ksb.user.domain.publisher.UserEventPublisher;
import com.benjamin.parsy.ksb.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserEventPublisher userEventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findUser_UserFound_ReturnUser() {

        // Given
        UUID orderUuid = UUID.randomUUID();
        User user = DataTestUtils.createUser();

        when(userRepository.findById(user.getUuid()))
                .thenReturn(Optional.of(user));

        // When
        userService.validateUser(orderUuid, user.getUuid());

        // Then
        verify(userRepository, times(1))
                .findById(any(UUID.class));

        verify(userEventPublisher, times(1))
                .publishUserValidated(any(UserValidatedEvent.class));

        verify(userEventPublisher, times(0))
                .publishOrderFailed(any(OrderFailedEvent.class));

        ArgumentCaptor<UserValidatedEvent> eventCaptor = ArgumentCaptor.forClass(UserValidatedEvent.class);
        verify(userEventPublisher).publishUserValidated(eventCaptor.capture());
        assertEquals(orderUuid, eventCaptor.getValue().orderUuid());

    }

    @Test
    void findUser_UserNotFound_ThrowException() {

        // Given
        UUID orderUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();

        when(userRepository.findById(userUuid))
                .thenReturn(Optional.empty());

        // When
        userService.validateUser(orderUuid, userUuid);

        // Then
        verify(userRepository, times(1))
                .findById(any(UUID.class));

        verify(userEventPublisher, times(0))
                .publishUserValidated(any(UserValidatedEvent.class));

        verify(userEventPublisher, times(1))
                .publishOrderFailed(any(OrderFailedEvent.class));

        ArgumentCaptor<OrderFailedEvent> eventCaptor = ArgumentCaptor.forClass(OrderFailedEvent.class);
        verify(userEventPublisher).publishOrderFailed(eventCaptor.capture());
        assertEquals(orderUuid, eventCaptor.getValue().orderUuid());
        assertEquals("User not found for id " + userUuid, eventCaptor.getValue().cause());

    }

}