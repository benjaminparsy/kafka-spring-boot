package com.benjamin.parsy.ksb.order.infrastructure.controller.rest;

import com.benjamin.parsy.ksb.order.entity.model.OrderStatus;
import com.benjamin.parsy.ksb.order.infrastructure.ITest;
import com.benjamin.parsy.ksb.order.infrastructure.controller.rest.dto.ResponseOrderApiDto;
import com.benjamin.parsy.ksb.order.infrastructure.shared.JsonHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderRestControllerTest extends ITest {

    @Autowired
    private MockMvc mockMvc;

    @Value("classpath:rest-request/order-request.json")
    private Resource rawRequestJson;

    @Test
    void createOrders_OrderOk_ReturnOrder() throws Exception {

        // Given
        String inputJson = String.format(rawRequestJson.getContentAsString(StandardCharsets.UTF_8),
                UUID.randomUUID(),
                UUID.randomUUID(), 1, 100.00,
                UUID.randomUUID(), 2, 100.00
        );

        // When
        String jsonResult = mockMvc.perform(post("/orders")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
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