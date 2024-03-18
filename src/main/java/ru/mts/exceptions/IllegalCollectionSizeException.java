package ru.mts.exceptions;

public class IllegalCollectionSizeException extends Exception {
    public IllegalCollectionSizeException(String message, long size) {
        super(message + size);
    }
}
