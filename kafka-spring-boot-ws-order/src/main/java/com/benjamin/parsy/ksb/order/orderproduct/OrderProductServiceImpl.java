package com.benjamin.parsy.ksb.order.orderproduct;

import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderProductServiceImpl extends GenericServiceImpl<OrderProduct> implements OrderProductService {

    public OrderProductServiceImpl(OrderProductRepository repository, MessageService messageService) {
        super(repository, messageService);
    }

}
