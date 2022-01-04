package com.github.ticherti.voteforlunch.exception;

public class TooLateToModifyException extends RuntimeException {
    public TooLateToModifyException(String message) {
        super(message);
    }
}
