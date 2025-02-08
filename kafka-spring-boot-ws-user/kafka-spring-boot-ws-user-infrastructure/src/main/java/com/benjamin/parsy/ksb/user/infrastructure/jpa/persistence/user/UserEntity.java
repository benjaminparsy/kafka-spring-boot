package com.benjamin.parsy.ksb.user.infrastructure.jpa.persistence.user;

import com.benjamin.parsy.ksb.user.infrastructure.jpa.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "users")
@Table(schema = "app", name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

}
