package com.benjamin.parsy.ksb.shared.kafka;

import com.benjamin.parsy.ksb.shared.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(schema = "events", name = "kafka_events")
public class KafKaEvent extends BaseEntity {

    @Column(name = "event_date")
    private OffsetDateTime eventDate;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "payload")
    private String payload;

}
