package com.github.ticherti.voteforlunch.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeUtil {
    public static final String DATE_TIME_PATTERN = "HH-mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private LocalTime deadlineTime;

    @Autowired
    public void setDeadlineTime(@Value("${vote-time:11-00}") String timeLine) {
        deadlineTime = LocalTime.parse(timeLine, DATE_TIME_FORMATTER);
    }

    public void setTimeLimit(LocalTime time) {
        deadlineTime = time;
    }

    public LocalTime getTimeLimit() {
        return deadlineTime;
    }
}