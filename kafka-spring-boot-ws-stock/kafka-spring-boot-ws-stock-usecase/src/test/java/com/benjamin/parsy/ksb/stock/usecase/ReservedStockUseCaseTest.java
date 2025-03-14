package com.benjamin.parsy.ksb.stock.usecase;

import com.benjamin.parsy.ksb.stock.entity.exception.StockNotFoundException;
import com.benjamin.parsy.ksb.stock.entity.gateway.EventGateway;
import com.benjamin.parsy.ksb.stock.entity.gateway.StockGateway;
import com.benjamin.parsy.ksb.stock.entity.model.Product;
import com.benjamin.parsy.ksb.stock.entity.model.Stock;
import com.benjamin.parsy.ksb.stock.entity.model.event.EventType;
import com.benjamin.parsy.ksb.stock.entity.model.event.StockReservedEvent;
import com.benjamin.parsy.ksb.stock.usecase.dto.TestDesiredProductPublicData;
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
    private StockGateway stockGateway;

    @Mock
    private EventGateway eventGateway;

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
            when(stockGateway.findByProductUuid(desiredProduct.productUuid()))
                    .thenReturn(new Stock(UUID.randomUUID(), new Product(desiredProduct.productUuid(), "name", 100.00), desiredProduct.quantity()));
        }

        // When
        reservedStockUseCase.reservedStock(orderUuid, List.copyOf(desiredProducts));

        // Then
        // Check stock gateway call
        verify(stockGateway, times(3)).findByProductUuid(any(UUID.class));

        ArgumentCaptor<Stock> stockCaptor = ArgumentCaptor.forClass(Stock.class);
        verify(stockGateway, times(3)).update(stockCaptor.capture());
        assertEquals(0, stockCaptor.getValue().getQuantity());

        // Check event
        ArgumentCaptor<StockReservedEvent> eventCaptor = ArgumentCaptor.forClass(StockReservedEvent.class);
        verify(eventGateway, times(1)).publish(eventCaptor.capture());
        assertEquals(EventType.STOCK_RESERVED, eventCaptor.getValue().getEventType());


    }

}