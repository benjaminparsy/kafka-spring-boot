package com.benjamin.parsy.ksb.user.infrastructure.jpa.persistence.kafkaevent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaEventRepository extends JpaRepository<KafKaEventEntity, Long> {
}
