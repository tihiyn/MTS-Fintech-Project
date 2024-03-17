package ru.mts.exceptions;

import java.io.IOException;

public class IllegalCollectionSizeException extends IOException {
    public IllegalCollectionSizeException(String message, long size) {
        super(message + size);
    }
}
