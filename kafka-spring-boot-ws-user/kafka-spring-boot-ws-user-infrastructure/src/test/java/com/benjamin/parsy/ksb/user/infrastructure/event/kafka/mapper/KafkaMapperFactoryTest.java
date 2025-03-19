package com.benjamin.parsy.ksb.user.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.ksb.user.entity.model.event.EventType;
import com.benjamin.parsy.ksb.user.infrastructure.IntegrationTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
class KafkaMapperFactoryTest extends IntegrationTest {

    @Autowired
    private KafkaMapperFactory kafkaMapperFactory;

    @ParameterizedTest
    @MethodSource("eventTypeAndMapperProvider")
    void testGetMapper(EventType eventType, Class<? extends KafkaEventMapper> expectedMapperClass) {

        // When
        KafkaEventMapper kafkaEventMapper = kafkaMapperFactory.get(eventType);

        // Then
        assertInstanceOf(expectedMapperClass, kafkaEventMapper);

    }

    static Stream<Arguments> eventTypeAndMapperProvider() {
        return Stream.of(
                Arguments.of(EventType.ORDER_FAILED, OrderFailedEventMapper.class),
                Arguments.of(EventType.USER_VALIDATED, UserValidatedEventMapper.class)
        );
    }

}