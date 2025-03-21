package com.benjamin.parsy.runnetic.user.infrastructure.db.jpa.schema;

import com.benjamin.parsy.runnetic.user.entity.model.User;
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

    public User toUser() {
        return new User(
                this.uuid,
                this.firstname,
                this.lastname,
                this.email,
                this.address,
                this.phone
        );
    }

}
