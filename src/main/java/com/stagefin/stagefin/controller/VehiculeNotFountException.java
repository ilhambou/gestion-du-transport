package com.stagefin.stagefin.controller;

public class VehiculeNotFountException extends Throwable {
    public VehiculeNotFountException(String message) {
        super(message);
    }
}