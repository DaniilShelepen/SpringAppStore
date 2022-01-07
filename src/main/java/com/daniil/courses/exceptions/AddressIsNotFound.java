package com.daniil.courses.exceptions;

public class AddressIsNotFound extends RuntimeException {
    public AddressIsNotFound(String message) {
        super(message);
    }
}
