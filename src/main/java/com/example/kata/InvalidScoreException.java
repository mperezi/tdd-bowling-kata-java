package com.example.kata;

public class InvalidScoreException extends RuntimeException {

    public InvalidScoreException() {
    }

    public InvalidScoreException(String message) {
        super(message);
    }
}
