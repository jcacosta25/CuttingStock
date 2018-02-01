package com.company;

@SuppressWarnings("serial")
public class InvalidLengthException extends RuntimeException {
    public InvalidLengthException(int length) {
        super("Piece with length: " + String.valueOf(length) + " exceeds the stock size");
    }

}
