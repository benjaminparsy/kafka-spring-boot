package com.benjamin.parsy.ksb.order.infrastructure.controller.rest;

import com.benjamin.parsy.ksb.order.entity.model.Order;
import com.benjamin.parsy.ksb.order.infrastructure.controller.rest.dto.DesiredProductPublicData;
import com.benjamin.parsy.ksb.order.infrastructure.controller.rest.dto.RequestOrderApiDto;
import com.benjamin.parsy.ksb.order.infrastructure.controller.rest.dto.ResponseOrderApiDto;
import com.benjamin.parsy.ksb.order.usecase.CreateOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderRestController implements com.benjamin.parsy.ksb.order.infrastructure.controller.rest.OrdersApi {

    private final CreateOrderUseCase createOrderUseCase;

    @Transactional
    @Override
    public ResponseEntity<ResponseOrderApiDto> createOrder(@Valid RequestOrderApiDto requestBody) {

        List<DesiredProductPublicData> products = requestBody.getProducts().stream()
                .map(p -> new DesiredProductPublicData(
                        p.getProductUuid(),
                        p.getQuantity(),
                        p.getPrice()))
                .toList();

        Order order = createOrderUseCase.createOrder(requestBody.getUserUuid(), List.copyOf(products));

        ResponseOrderApiDto response = new ResponseOrderApiDto();
        response.setOrderUuid(order.getUuid().toString());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus().name());
        response.setTotalPrice(order.getTotalPrice());

        return ResponseEntity.ok(response);
    }

}
