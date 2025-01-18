package com.benjamin.parsy.ksb.order.order;

import com.benjamin.parsy.ksb.order.shared.OrderErrorCode;
import com.benjamin.parsy.ksb.order.shared.exception.StockException;
import com.benjamin.parsy.ksb.order.shared.exception.UserProjectionNotFoundException;
import com.benjamin.parsy.ksb.order.stockprojection.StockProjectionService;
import com.benjamin.parsy.ksb.order.userprojection.UserProjection;
import com.benjamin.parsy.ksb.order.userprojection.UserProjectionService;
import com.benjamin.parsy.ksb.shared.service.jpa.GenericServiceImpl;
import com.benjamin.parsy.ksb.shared.service.message.MessageService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Map;

@Service
public class OrderServiceImpl extends GenericServiceImpl<Order> implements OrderService {

    private final OrderRepository orderRepository;
    private final UserProjectionService userProjectionService;
    private final StockProjectionService stockProjectionService;
    private final MessageService messageService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserProjectionService userProjectionService,
                            StockProjectionService stockProjectionService, MessageService messageService) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.userProjectionService = userProjectionService;
        this.stockProjectionService = stockProjectionService;
        this.messageService = messageService;
    }

    @Override
    public Order createOrder(OffsetDateTime orderDate, String orderStatus, Long userProjectionId, Map<Long, Integer> quantityByProductId) throws StockException, UserProjectionNotFoundException {

        UserProjection userProjection = userProjectionService.findById(userProjectionId)
                .orElseThrow(() -> new UserProjectionNotFoundException(
                        messageService.getErrorMessage(OrderErrorCode.USERPROJECTION_NOT_FOUND.getCode(), userProjectionId)
                ));

        stockProjectionService.checkQuantity(quantityByProductId);
        int totalPrice = stockProjectionService.getTotalPrice(quantityByProductId);

        return orderRepository.save(Order.builder()
                .orderDate(orderDate != null ? orderDate : OffsetDateTime.now())
                .orderStatusEnum(OrderStatusEnum.safeValueOf(orderStatus).orElse(OrderStatusEnum.CREATED))
                .orderTotal(totalPrice)
                .userProjection(userProjection)
                .build());
    }

}
