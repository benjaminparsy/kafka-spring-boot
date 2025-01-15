package com.benjamin.parsy.ksb.order.order;

import com.benjamin.parsy.ksb.order.api.OrdersApi;
import com.benjamin.parsy.ksb.order.model.ProductApiDto;
import com.benjamin.parsy.ksb.order.model.RequestOrderApiDto;
import com.benjamin.parsy.ksb.order.model.ResponseOrderApiDto;
import com.benjamin.parsy.ksb.order.order.kafka.KafkaProducerOrderService;
import com.benjamin.parsy.ksb.order.orderproduct.OrderProduct;
import com.benjamin.parsy.ksb.order.orderproduct.OrderProductService;
import com.benjamin.parsy.ksb.order.shared.KafkaConstant;
import com.benjamin.parsy.ksb.order.shared.OrderErrorCode;
import com.benjamin.parsy.ksb.order.shared.exception.StockException;
import com.benjamin.parsy.ksb.order.shared.exception.UserProjectionNotFoundException;
import com.benjamin.parsy.ksb.shared.exception.BusinessException;
import com.benjamin.parsy.ksb.shared.exception.RestException;
import com.benjamin.parsy.ksb.shared.service.message.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderRestController implements OrdersApi {

    private final MessageService messageService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final KafkaProducerOrderService kafkaProducerOrderService;

    @Transactional
    @Override
    public ResponseEntity<ResponseOrderApiDto> createOrder(@Valid RequestOrderApiDto requestBody) {

        Map<Long, Integer> quantityByProductId = mapQuantityByProductId(requestBody);

        Order order;
        try {
            order = orderService.createOrder(requestBody.getOrderDate(), String.valueOf(requestBody.getOrderStatus()),
                    requestBody.getUserId(), quantityByProductId);
        } catch (StockException | UserProjectionNotFoundException e) {
            throw new RestException(e.getErrorMessage());
        }

        List<OrderProduct> orderProductList = orderProductService.createOrderProductList(quantityByProductId, order);

        try {
            kafkaProducerOrderService.send(KafkaConstant.TOPIC_ORDER_CREATED, orderProductList);
        } catch (BusinessException e) {
            throw new RestException(e.getErrorMessage());
        } catch (JsonProcessingException e) {
            throw new RestException(messageService.getErrorMessage(OrderErrorCode.CANNOT_JSONIFY_OBJECT.getCode()));
        }

        return ResponseEntity.ok(createResponse(order));
    }

    /**
     * Transforms the list of products in the input request into map of desired quantity per product
     *
     * @param requestBody Input request body
     * @return Map of desired quantity per product
     */
    private Map<Long, Integer> mapQuantityByProductId(RequestOrderApiDto requestBody) {

        return requestBody.getProducts()
                .stream()
                .collect(Collectors.toMap(
                        ProductApiDto::getProductId,
                        ProductApiDto::getQuantity
                ));
    }

    private ResponseOrderApiDto createResponse(Order order) {

        ResponseOrderApiDto response = new ResponseOrderApiDto();
        response.setOrderId(order.getId());
        response.setMessage("Order created successfully");
        response.setStatus(OrderStatusEnum.CREATED.name());
        response.setOrderTotal((double) order.getOrderTotal());

        return response;
    }

}
