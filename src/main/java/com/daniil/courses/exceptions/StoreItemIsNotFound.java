package com.daniil.courses.exceptions;

public class StoreItemIsNotFound extends RuntimeException {
    public StoreItemIsNotFound(String message) {
        super(message);
    }
}
