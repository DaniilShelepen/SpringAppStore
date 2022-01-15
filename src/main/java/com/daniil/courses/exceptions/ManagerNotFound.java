package com.daniil.courses.exceptions;

public class ManagerNotFound extends RuntimeException{
    public ManagerNotFound(String message) {
        super(message);
    }
}
