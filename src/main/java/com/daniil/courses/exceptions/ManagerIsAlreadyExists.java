package com.daniil.courses.exceptions;

public class ManagerIsAlreadyExists extends RuntimeException{
    public ManagerIsAlreadyExists(String message) {
        super(message);
    }
}
