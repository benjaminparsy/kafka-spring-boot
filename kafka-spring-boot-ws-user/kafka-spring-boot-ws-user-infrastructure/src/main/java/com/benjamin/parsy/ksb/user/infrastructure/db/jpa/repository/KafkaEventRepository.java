package com.benjamin.parsy.ksb.user.infrastructure.db.jpa.repository;

import com.benjamin.parsy.ksb.user.infrastructure.db.jpa.schema.KafKaEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaEventRepository extends JpaRepository<KafKaEventEntity, Long> {
}
