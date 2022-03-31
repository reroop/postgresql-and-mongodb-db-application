package com.dbapplication.utils.mongodb;

import com.dbapplication.models.mongo.embedded.EmbeddedPerson;
import com.dbapplication.models.mongo.reference.Person;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
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


    public boolean isMongoPersonInfoValid(Person person) {
        if (!isDateInRange1900to2100(person.getBirth_date())) {
            log.info("add person, birthdate not in range 1900-2100");
            return false;
        }
        if (person.getGiven_name() == null && person.getSurname() == null ||
                Objects.equals(person.getGiven_name(), "") && Objects.equals(person.getSurname(), "")) {
            log.info("add person, given name or surname must be set");
            return false;
        }
        if (!isFirstDateBeforeSecondDate(person.getBirth_date(), person.getReg_time())) {
            log.info("add person, birth date not before reg time");
            return false;
        }
        if (!isDateInRange2010to2100(person.getReg_time())) {
            log.info("add person, reg time not in rage 2010-2100");
            return false;
        }
        return true;
    }


    public boolean isMongoEmbeddedPersonInfoValid(EmbeddedPerson embeddedPerson) {
        if (!isDateInRange1900to2100(embeddedPerson.getBirth_date())) {
            log.info("add person, birthdate not in range 1900-2100");
            return false;
        }
        if (embeddedPerson.getGiven_name() == null && embeddedPerson.getSurname() == null ||
                Objects.equals(embeddedPerson.getGiven_name(), "") && Objects.equals(embeddedPerson.getSurname(), "")) {
            log.info("add person, given name or surname must be set");
            return false;
        }
        if (!isFirstDateBeforeSecondDate(embeddedPerson.getBirth_date(), embeddedPerson.getReg_time())) {
            log.info("add person, birth date not before reg time");
            return false;
        }
        if (!isDateInRange2010to2100(embeddedPerson.getReg_time())) {
            log.info("add person, reg time not in range 2010-2100");
            return false;
        }
        return true;
    }
}
