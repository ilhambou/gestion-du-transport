package com.stagefin.stagefin.controller;

public class CommandeNotFoundException extends RuntimeException {

    public CommandeNotFoundException(String message) {
        super(message);
    }
}
