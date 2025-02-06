package com.benjamin.parsy.ksb.order.application.rest;

import com.benjamin.parsy.ksb.order.application.rest.model.RequestOrderApiDto;
import com.benjamin.parsy.ksb.order.application.rest.model.ResponseOrderApiDto;
import com.benjamin.parsy.ksb.order.domain.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.domain.model.Order;
import com.benjamin.parsy.ksb.order.domain.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderRestController implements OrdersApi {

    private final OrderService orderService;

    @Transactional
    @Override
    public ResponseEntity<ResponseOrderApiDto> createOrder(@Valid RequestOrderApiDto requestBody) {

        List<DesiredProduct> products = requestBody.getProducts().stream()
                .map(p -> new DesiredProduct(
                        p.getProductUuid(),
                        p.getQuantity(),
                        p.getPrice()))
                .toList();

        Order order = orderService.createOrder(requestBody.getUserUuid(), products);

        return ResponseEntity.ok(createResponse(order));
    }

    private ResponseOrderApiDto createResponse(Order order) {

        ResponseOrderApiDto response = new ResponseOrderApiDto();
        response.setOrderUuid(order.getUuid().toString());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus().name());
        response.setTotalPrice(order.getTotalPrice());

        return response;
    }

}
