package com.benjamin.parsy.ksb.shared.exception;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String searchId) {
        super(String.format("Item with id '%s' cannot be found in database.", searchId));
    }

}
