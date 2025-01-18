package com.benjamin.parsy.ksb.order.userprojection.kafka;

import com.benjamin.parsy.ksb.order.KafkaTest;
import com.benjamin.parsy.ksb.order.shared.KafkaConstant;
import com.benjamin.parsy.ksb.order.userprojection.UserProjection;
import com.benjamin.parsy.ksb.order.userprojection.UserProjectionRepository;
import com.benjamin.parsy.ksb.order.userprojection.UserProjectionService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(topics = {
        KafkaConstant.TOPIC_USER_CREATED,
        KafkaConstant.TOPIC_USER_UPDATED
})
class UserProjectionKafkaConsumerTest extends KafkaTest {

    @Autowired
    private KafkaTemplate<String, UserProjectionEvent> kafkaTemplate;

    @Autowired
    private UserProjectionRepository userProjectionRepository;

    @Autowired
    private UserProjectionService userProjectionService;

    @Autowired
    public UserProjectionKafkaConsumerTest(EmbeddedKafkaBroker embeddedKafka) {
        super(embeddedKafka);
    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void handleUserCreated_EventOk_CreateUser() {

        // Given
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.internet().safeEmailAddress();
        String address = faker.address().fullAddress();
        String phone = "0123456789";

        UserProjectionEvent userProjectionEvent = UserProjectionEvent.builder()
                .name(name)
                .email(email)
                .address(address)
                .phone(phone)
                .build();

        int numberOfItems = userProjectionRepository.findAll().size();

        // When
        kafkaTemplate.send(KafkaConstant.TOPIC_USER_CREATED, userProjectionEvent);

        // Then
        await().until(() -> userProjectionRepository.findAll().size() == (numberOfItems + 1));

        Optional<UserProjection> optionalUserCreated = userProjectionRepository.findByEmail(email);

        assertTrue(optionalUserCreated.isPresent());
        assertNotNull(optionalUserCreated.get().getId());
        assertEquals(name, optionalUserCreated.get().getName());
        assertEquals(email, optionalUserCreated.get().getEmail());
        assertEquals(address, optionalUserCreated.get().getAddress());
        assertEquals(phone, optionalUserCreated.get().getPhone());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void handleUserUpdated_EventOk_UpdateUser() {

        // Given
        long firstId = userProjectionRepository.findAll().getFirst().getId();

        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.internet().safeEmailAddress();
        String address = faker.address().fullAddress();
        String phone = "0123456789";

        UserProjectionEvent userProjectionEvent = UserProjectionEvent.builder()
                .userId(firstId)
                .name(name)
                .email(email)
                .address(address)
                .phone(phone)
                .build();

        // When
        kafkaTemplate.send(KafkaConstant.TOPIC_USER_UPDATED, userProjectionEvent);

        // Then
        await().until(() -> userProjectionService.updateUser(firstId, name, email, address, phone).getEmail(), equalTo(email));

        Optional<UserProjection> optionalUserUpdated = userProjectionRepository.findById(firstId);

        assertTrue(optionalUserUpdated.isPresent());
        assertNotNull(optionalUserUpdated.get().getId());
        assertEquals(name, optionalUserUpdated.get().getName());
        assertEquals(email, optionalUserUpdated.get().getEmail());
        assertEquals(address, optionalUserUpdated.get().getAddress());
        assertEquals(phone, optionalUserUpdated.get().getPhone());

    }

}