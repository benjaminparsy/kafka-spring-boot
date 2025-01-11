package com.benjamin.parsy.ksb.order.kafkaevent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaEventRepository extends JpaRepository<KafKaEvent, Long> {
}
