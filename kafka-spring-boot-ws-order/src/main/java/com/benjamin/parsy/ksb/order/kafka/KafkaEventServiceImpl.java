package com.benjamin.parsy.ksb.order.kafka;

import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventServiceImpl extends GenericServiceImpl<KafKaEvent> implements KafkaEventService {

    public KafkaEventServiceImpl(KafkaEventRepository repository, MessageService messageService) {
        super(repository, messageService);
    }

}
