package com.benjamin.parsy.ksb.order.kafka;

import com.benjamin.parsy.ksb.shared.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "kafka_events")
@Table(schema = "app", name = "kafka_events")
public class KafKaEvent extends BaseEntity {

    @Column(name = "event_date")
    private OffsetDateTime eventDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private KafkaEventTypeEnum eventType;

    @Column(name = "payload")
    private String payload;

}
