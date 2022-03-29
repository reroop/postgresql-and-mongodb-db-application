package com.dbapplication.utils.mongodb;

import java.time.LocalDateTime;

public class ValidationChecks {
    // universal helper class for validating info

    //todo: mongo Update has currentTimestamp <- test this

    public boolean isFirstDateBeforeSecondDate(LocalDateTime firstDate, LocalDateTime secondDate) {
        return firstDate.isBefore(secondDate);
    }

    public boolean isDateInRange2010to2100(LocalDateTime date) {
        LocalDateTime rangeStart = LocalDateTime.of(2009, 12, 31, 23,59,59);
        LocalDateTime rangeEnd = LocalDateTime.of(2101, 1, 1, 0,0,0);
        return date.isAfter(rangeStart) && date.isBefore(rangeEnd);
    }

    public boolean isDateInRange1900to2100(LocalDateTime date) {
        LocalDateTime rangeStart = LocalDateTime.of(1899, 12, 31, 23,59,59);
        LocalDateTime rangeEnd = LocalDateTime.of(2101, 1, 1, 0,0,0);
        return date.isAfter(rangeStart) && date.isBefore(rangeEnd);
    }
}
