package com.benjamin.parsy.ksb.shared.testclass;

import com.benjamin.parsy.ksb.shared.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@jakarta.persistence.Entity(name = "sub_entity")
@Table(name = "sub_entity")
public class SubEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(targetEntity = Entity.class, fetch = FetchType.LAZY, optional = false)
    private Entity entity;

}
