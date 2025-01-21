package com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.kafkaevent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaEventRepository extends JpaRepository<KafKaEventEntity, Long> {
}
