package com.benjamin.parsy.ksb.order.application.rest;

import com.benjamin.parsy.ksb.order.application.ApplicationTest;
import com.benjamin.parsy.ksb.order.application.rest.helper.JsonHelper;
import com.benjamin.parsy.ksb.order.application.rest.model.ProductApiDto;
import com.benjamin.parsy.ksb.order.application.rest.model.RequestOrderApiDto;
import com.benjamin.parsy.ksb.order.application.rest.model.ResponseOrderApiDto;
import com.benjamin.parsy.ksb.order.domain.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.domain.model.Order;
import com.benjamin.parsy.ksb.order.domain.model.OrderStatus;
import com.benjamin.parsy.ksb.order.domain.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderRestControllerTest extends ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void createOrders_OrderOk_ReturnOrder() throws Exception {

        // Given
        UUID userUuid = UUID.randomUUID();

        RequestOrderApiDto requestOrderApiDto = new RequestOrderApiDto();
        requestOrderApiDto.setUserUuid(userUuid);
        requestOrderApiDto.setProducts(List.of(
                new ProductApiDto(UUID.randomUUID(), 1, 100.00),
                new ProductApiDto(UUID.randomUUID(), 2, 100.00)
        ));

        List<DesiredProduct> products = requestOrderApiDto.getProducts()
                .stream()
                .map(p -> new DesiredProduct(p.getProductUuid(), p.getQuantity(), p.getPrice()))
                .toList();

        Order order = new Order(userUuid, products);

        when(orderService.createOrder(requestOrderApiDto.getUserUuid(), products))
                .thenReturn(order);

        // When
        String json = JsonHelper.toJson(requestOrderApiDto);

        String jsonResult = mockMvc.perform(post("/orders")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        ResponseOrderApiDto body = JsonHelper.toEntity(jsonResult, ResponseOrderApiDto.class);

        assertNotNull(body.getOrderUuid());
        assertNotNull(body.getOrderDate());
        assertEquals(OrderStatus.PENDING.name(), body.getStatus());
        assertEquals(300, body.getTotalPrice());

    }

}