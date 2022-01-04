package com.github.ticherti.voteforlunch.exception;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TooLateToVoteException extends RuntimeException {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public TooLateToVoteException(LocalTime time) {
        super("Too late to vote at " + time.format(DATE_TIME_FORMATTER));
    }
}
