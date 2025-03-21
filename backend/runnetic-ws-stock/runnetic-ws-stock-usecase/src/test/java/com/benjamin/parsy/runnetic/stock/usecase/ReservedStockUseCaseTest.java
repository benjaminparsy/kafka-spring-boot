package com.benjamin.parsy.runnetic.stock.usecase;

import com.benjamin.parsy.runnetic.stock.usecase.exception.StockNotFoundException;
import com.benjamin.parsy.runnetic.stock.usecase.port.EventPort;
import com.benjamin.parsy.runnetic.stock.usecase.port.StockPort;
import com.benjamin.parsy.runnetic.stock.entity.model.Product;
import com.benjamin.parsy.runnetic.stock.entity.model.Stock;
import com.benjamin.parsy.runnetic.stock.entity.model.event.EventType;
import com.benjamin.parsy.runnetic.stock.entity.model.event.StockReservedEvent;
import com.benjamin.parsy.runnetic.stock.usecase.publicdata.TestDesiredProductPublicData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReservedStockUseCaseTest {

    @InjectMocks
    private ReservedStockUseCase reservedStockUseCase;

    @Mock
    private StockPort stockPort;

    @Mock
    private EventPort eventPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void reservedStock() throws StockNotFoundException {

        // Given
        UUID orderUuid = UUID.randomUUID();

        List<TestDesiredProductPublicData> desiredProducts = DataTestUtils.createDesiredProducts();

        for (TestDesiredProductPublicData desiredProduct : desiredProducts) {
            when(stockPort.findByProductUuid(desiredProduct.productUuid()))
                    .thenReturn(new Stock(UUID.randomUUID(), new Product(desiredProduct.productUuid(), "name", 100.00), desiredProduct.quantity()));
        }

        // When
        reservedStockUseCase.reservedStock(orderUuid, List.copyOf(desiredProducts));

        // Then
        // Check stock gateway call
        verify(stockPort, times(3)).findByProductUuid(any(UUID.class));

        ArgumentCaptor<Stock> stockCaptor = ArgumentCaptor.forClass(Stock.class);
        verify(stockPort, times(3)).update(stockCaptor.capture());
        assertEquals(0, stockCaptor.getValue().getQuantity());

        // Check event
        ArgumentCaptor<StockReservedEvent> eventCaptor = ArgumentCaptor.forClass(StockReservedEvent.class);
        verify(eventPort, times(1)).publish(eventCaptor.capture());
        assertEquals(EventType.STOCK_RESERVED, eventCaptor.getValue().getEventType());


    }

}