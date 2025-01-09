package com.benjamin.parsy.ksb.order.stockprojection;

import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class StockProjectionServiceImpl extends GenericServiceImpl<StockProjection> implements StockProjectionService {

    public StockProjectionServiceImpl(StockProjectionRepository repository, MessageService messageService) {
        super(repository, messageService);
    }

}
