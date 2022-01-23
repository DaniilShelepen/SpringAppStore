package com.daniil.courses.exceptions;

public class BasketIsEmpty extends RuntimeException{
    public BasketIsEmpty(String message) {
        super(message);
    }
}
