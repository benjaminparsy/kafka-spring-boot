package com.benjamin.parsy.ksb.analytics.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.Map;

@Data
@Document(indexName = "event_logs")
public class EventLog {

    @Id
    private String id;

    @Field(type = FieldType.Date)
    private Instant timestamp;

    @Field(type = FieldType.Keyword)
    private String eventType;

    @Field(type = FieldType.Keyword)
    private String eventSource;

    @Field(type = FieldType.Object)
    private Map<String, Object> payload;

    @Field(type = FieldType.Integer)
    private int partition;

    @Field(type = FieldType.Long)
    private long offset;

}