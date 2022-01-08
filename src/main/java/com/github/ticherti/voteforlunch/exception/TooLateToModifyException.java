package com.github.ticherti.voteforlunch.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class TooLateToModifyException extends AppException {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public TooLateToModifyException(LocalTime time) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, ("Too late to change " + time.format(DATE_TIME_FORMATTER)), ErrorAttributeOptions.of(MESSAGE));
    }

    public TooLateToModifyException(LocalDate date) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, ("Too late to change " + date), ErrorAttributeOptions.of(MESSAGE));
    }
}
