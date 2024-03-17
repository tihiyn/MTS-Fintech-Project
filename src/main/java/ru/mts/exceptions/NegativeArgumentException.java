package ru.mts.exceptions;

public class NegativeArgumentException extends IllegalArgumentException{
    public NegativeArgumentException(String message) {
        super(message);
    }
}
