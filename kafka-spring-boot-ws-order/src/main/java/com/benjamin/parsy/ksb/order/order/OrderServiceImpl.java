package com.benjamin.parsy.ksb.order.order;

import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends GenericServiceImpl<Order> implements OrderService {

    public OrderServiceImpl(OrderRepository repository, MessageService messageService) {
        super(repository, messageService);
    }

}
