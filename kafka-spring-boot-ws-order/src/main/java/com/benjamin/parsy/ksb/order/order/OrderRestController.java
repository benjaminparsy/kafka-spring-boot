package com.benjamin.parsy.ksb.order.order;

import com.benjamin.parsy.ksb.order.api.OrdersApi;
import com.benjamin.parsy.ksb.order.exception.StockException;
import com.benjamin.parsy.ksb.order.model.ProductApiDto;
import com.benjamin.parsy.ksb.order.model.RequestOrderApiDto;
import com.benjamin.parsy.ksb.order.model.ResponseOrderApiDto;
import com.benjamin.parsy.ksb.order.orderproduct.OrderProductService;
import com.benjamin.parsy.ksb.order.stockprojection.StockProjectionService;
import com.benjamin.parsy.ksb.order.userprojection.UserProjection;
import com.benjamin.parsy.ksb.order.userprojection.UserProjectionService;
import com.benjamin.parsy.ksb.shared.exception.ErrorCode;
import com.benjamin.parsy.ksb.shared.exception.RestException;
import com.benjamin.parsy.ksb.shared.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class OrderRestController implements OrdersApi {

    private final UserProjectionService userProjectionService;
    private final MessageService messageService;
    private final StockProjectionService stockProjectionService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;

    public OrderRestController(UserProjectionService userProjectionService,
                               MessageService messageService,
                               StockProjectionService stockProjectionService,
                               OrderService orderService,
                               OrderProductService orderProductService) {
        this.userProjectionService = userProjectionService;
        this.messageService = messageService;
        this.stockProjectionService = stockProjectionService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @Override
    public ResponseEntity<ResponseOrderApiDto> createOrder(@Valid RequestOrderApiDto requestBody) {

        UserProjection userProjection = userProjectionService.findById(requestBody.getUserId())
                .orElseThrow(() -> new RestException(
                        messageService.getErrorMessage(ErrorCode.BR1, requestBody.getUserId())
                ));

        Map<Long, Integer> quantityByProductId = mapQuantityByProductId(requestBody);

        try {
            stockProjectionService.checkQuantity(quantityByProductId);
        } catch (StockException e) {
            throw new RestException(e.getErrorMessage());
        }

        int totalPrice = stockProjectionService.getTotalPrice(quantityByProductId);

        Order order = orderService.createOrder(requestBody.getOrderDate(), String.valueOf(requestBody.getOrderStatus()),
                userProjection, totalPrice);

        orderProductService.createOrderProductList(quantityByProductId, order);

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
