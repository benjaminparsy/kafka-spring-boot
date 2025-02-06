package com.benjamin.parsy.ksb.shared.kafka;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaEventRepository extends JpaRepository<KafKaEvent, Long> {
}
