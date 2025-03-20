package com.benjamin.parsy.runnetic.user.entity.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class User {

    private final UUID uuid;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String address;
    private final String phone;

    public User(UUID uuid, String firstname, String lastname, String email, String address, String phone) {
        this.uuid = uuid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

}
