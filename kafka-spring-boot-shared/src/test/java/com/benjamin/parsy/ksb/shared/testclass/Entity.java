package com.benjamin.parsy.ksb.shared.testclass;

import com.benjamin.parsy.ksb.shared.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@jakarta.persistence.Entity(name = "entity")
@Table(name = "entity")
public class Entity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

}
