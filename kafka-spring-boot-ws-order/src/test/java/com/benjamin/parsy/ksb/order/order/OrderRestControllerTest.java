package com.benjamin.parsy.ksb.order.order;

import com.benjamin.parsy.ksb.order.model.ProductApiDto;
import com.benjamin.parsy.ksb.order.model.RequestOrderApiDto;
import com.benjamin.parsy.ksb.order.model.ResponseOrderApiDto;
import com.benjamin.parsy.ksb.order.stockprojection.StockProjectionService;
import com.benjamin.parsy.ksb.order.userprojection.UserProjection;
import com.benjamin.parsy.ksb.order.userprojection.UserProjectionRepository;
import com.benjamin.parsy.ksb.shared.helper.JsonHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserProjectionRepository userProjectionRepository;

    @Autowired
    private StockProjectionService stockProjectionService;

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void createOrders_OrderOk_ReturnOrder() throws Exception {

        // Given
        UserProjection userProjection = userProjectionRepository.findAll().getFirst();

        List<ProductApiDto> productApiDtoList = stockProjectionService.findAll().stream()
                .map(s -> {
                    ProductApiDto productApiDto = new ProductApiDto();
                    productApiDto.setProductId(s.getProductId());
                    productApiDto.setQuantity(1);
                    return productApiDto;
                }).toList();

        RequestOrderApiDto requestOrderApiDto = new RequestOrderApiDto();
        requestOrderApiDto.setUserId(userProjection.getId());
        requestOrderApiDto.setProducts(productApiDtoList);

        String json = JsonHelper.toJson(requestOrderApiDto);

        // When
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
        ResponseOrderApiDto responseOrderApiDto = JsonHelper.toEntity(jsonResult, ResponseOrderApiDto.class);

        assertNotNull(responseOrderApiDto.getOrderId());
        assertNotNull(responseOrderApiDto.getMessage());
        assertNotNull(OrderStatusEnum.CREATED.name(), responseOrderApiDto.getStatus());
        assertTrue(responseOrderApiDto.getOrderTotal() > 0);

    }

}