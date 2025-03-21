package com.benjamin.parsy.runnetic.stock.infrastructure.event.kafka;

import com.benjamin.parsy.runnetic.avro.dto.DesiredProductKafkaEvent;
import com.benjamin.parsy.runnetic.avro.dto.OrderCreatedKafkaEvent;
import com.benjamin.parsy.runnetic.stock.usecase.ReservedStockUseCase;
import com.benjamin.parsy.runnetic.stock.usecase.publicdata.IDesiredProductPublicData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class KafkaEventListenerTest {

    @Mock
    private ReservedStockUseCase reservedStockUseCase;

    @InjectMocks
    private KafkaEventListener kafkaEventListener;

    @Captor
    private ArgumentCaptor<List<IDesiredProductPublicData>> productsCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleOrderCreated() {

        // Given
        UUID uuid = UUID.randomUUID();
        List<DesiredProductKafkaEvent> desiredProductKafkaEventList = List.of(
                new DesiredProductKafkaEvent(UUID.randomUUID().toString(), 10, 100.00),
                new DesiredProductKafkaEvent(UUID.randomUUID().toString(), 20, 200.00),
                new DesiredProductKafkaEvent(UUID.randomUUID().toString(), 30, 300.00)
        );

        OrderCreatedKafkaEvent orderCreatedKafkaEvent = new OrderCreatedKafkaEvent();
        orderCreatedKafkaEvent.setOrderUuid(uuid.toString());
        orderCreatedKafkaEvent.setProducts(desiredProductKafkaEventList);

        // When
        kafkaEventListener.handleOrderCreated(orderCreatedKafkaEvent);

        // Then
        verify(reservedStockUseCase, times(1)).reservedStock(uuidCaptor.capture(), productsCaptor.capture());

        assertEquals(uuid, uuidCaptor.getValue());
        assertEquals(3, productsCaptor.getValue().size());


    }

}