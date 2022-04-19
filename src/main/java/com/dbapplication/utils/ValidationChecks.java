package com.dbapplication.utils;

import com.dbapplication.models.mongo.embedded.EmbeddedPerson;
import com.dbapplication.models.mongo.reference.Person;
import com.dbapplication.models.postgre.jsonb.emb.PersonEmb;
import com.dbapplication.models.postgre.jsonb.ref.PersonRef;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Slf4j
public class ValidationChecks {
    // universal helper class for validating info

    public static boolean isFirstDateBeforeSecondDate(LocalDateTime firstDate, LocalDateTime secondDate) {
        return firstDate.isBefore(secondDate);
    }

    public static boolean isDateInRange2010to2100(LocalDateTime date) {
        LocalDateTime rangeStart = LocalDateTime.of(2009, 12, 31, 23,59,59);
        LocalDateTime rangeEnd = LocalDateTime.of(2101, 1, 1, 0,0,0);
        return date.isAfter(rangeStart) && date.isBefore(rangeEnd);
    }

    public static boolean isDateInRange1900to2100(LocalDateTime date) {
        LocalDateTime rangeStart = LocalDateTime.of(1899, 12, 31, 23,59,59);
        LocalDateTime rangeEnd = LocalDateTime.of(2101, 1, 1, 0,0,0);
        return date.isAfter(rangeStart) && date.isBefore(rangeEnd);
    }

    public static boolean isPostgreJsonRefPersonValid(PersonRef personRef) throws Throwable {
        if (personRef.getData().getBirth_date() == null) {
            log.info("person birthdate is null");
            throw new Exception(new Throwable("Person birthdate must be set!"));
        }
        LocalDateTime birthDateInLocalDateTime =  LocalDateTime.ofInstant(personRef.getData().getBirth_date().toInstant(), ZoneId.systemDefault());

        if (!isDateInRange1900to2100(birthDateInLocalDateTime)) {
            log.info("person birthdate not in range 1900-2100! Current birthdate: " + birthDateInLocalDateTime);
            throw new Exception(new Throwable("person birthdate not in range 1900-2100! Current birthdate: " + birthDateInLocalDateTime));
        }
        if (!isFirstDateBeforeSecondDate(birthDateInLocalDateTime, personRef.getData().getReg_time())) {
            log.info("person birthdate (" + birthDateInLocalDateTime + ") is after reg time " + personRef.getData().getReg_time());
            throw new Exception(new Throwable("person birthdate (" + birthDateInLocalDateTime + ") is after reg time " + personRef.getData().getReg_time()));
        }
        if (!isDateInRange2010to2100(personRef.getData().getReg_time())) {
            log.info("person reg. time not in range 2010-2100! Current reg. time: " + personRef.getData().getReg_time());
            throw new Exception(new Throwable("person reg. time not in range 2010-2100! Current reg. time: " + personRef.getData().getReg_time()));
        }
        return true;
    }

    public static boolean isPostgreJsonEmbPersonValid(PersonEmb personEmb) throws Throwable {
        if (personEmb.getData().getBirth_date() == null) {
            log.info("person birthdate is null");
            throw new Exception(new Throwable("Person birthdate must be set!"));
        }
        LocalDateTime birthDateInLocalDateTime =  LocalDateTime.ofInstant(personEmb.getData().getBirth_date().toInstant(), ZoneId.systemDefault());

        if (!isDateInRange1900to2100(birthDateInLocalDateTime)) {
            log.info("person birthdate not in range 1900-2100! Current birthdate: " + birthDateInLocalDateTime);
            throw new Exception(new Throwable("person birthdate not in range 1900-2100! Current birthdate: " + birthDateInLocalDateTime));
        }
        if (!isFirstDateBeforeSecondDate(birthDateInLocalDateTime, personEmb.getData().getReg_time())) {
            log.info("person birthdate (" + birthDateInLocalDateTime + ") is after reg time " + personEmb.getData().getReg_time());
            throw new Exception(new Throwable("person birthdate (" + birthDateInLocalDateTime + ") is after reg time " + personEmb.getData().getReg_time()));
        }
        if (!isDateInRange2010to2100(personEmb.getData().getReg_time())) {
            log.info("person reg. time not in range 2010-2100! Current reg. time: " + personEmb.getData().getReg_time());
            throw new Exception(new Throwable("person reg. time not in range 2010-2100! Current reg. time: " + personEmb.getData().getReg_time()));
        }
        return true;
    }


    public static boolean isMongoPersonInfoValid(Person person) throws Throwable {
        if (!isDateInRange1900to2100(person.getBirth_date())) {
            log.info("person, birthdate not in range 1900-2100");
            throw new Exception(new Throwable("person birthdate not in range 1900-2100! Current birthdate: " + person.getBirth_date()));
        }
        if (person.getGiven_name() == null && person.getSurname() == null ||
                Objects.equals(person.getGiven_name(), "") && Objects.equals(person.getSurname(), "")) {
            log.info("person, given name or surname must be set");
            throw new Exception(new Throwable("given name or surname must be set!"));
        }
        if (!isFirstDateBeforeSecondDate(person.getBirth_date(), person.getReg_time())) {
            log.info("person birthdate (" + person.getBirth_date() + ") is after reg time " + person.getReg_time());
            throw new Exception(new Throwable("person birthdate (" + person.getBirth_date() + ") is after reg time " + person.getReg_time()));
        }
        if (!isDateInRange2010to2100(person.getReg_time())) {
            log.info("person reg. time not in range 2010-2100! Current reg. time: " + person.getReg_time());
            throw new Exception(new Throwable("person reg. time not in range 2010-2100! Current reg. time: " + person.getReg_time()));
        }
        return true;
    }


    public static boolean isMongoEmbeddedPersonInfoValid(EmbeddedPerson embeddedPerson) throws Throwable {
        if (!isDateInRange1900to2100(embeddedPerson.getBirth_date())) {
            log.info("person, birthdate not in range 1900-2100");
            throw new Exception(new Throwable("person birthdate not in range 1900-2100! Current birthdate: " + embeddedPerson.getBirth_date()));
        }
        if (embeddedPerson.getGiven_name() == null && embeddedPerson.getSurname() == null ||
                Objects.equals(embeddedPerson.getGiven_name(), "") && Objects.equals(embeddedPerson.getSurname(), "")) {
            log.info("person, given name or surname must be set");
            throw new Exception(new Throwable("given name or surname must be set!"));
        }
        if (!isFirstDateBeforeSecondDate(embeddedPerson.getBirth_date(), embeddedPerson.getReg_time())) {
            log.info("person birthdate (" + embeddedPerson.getBirth_date() + ") is after reg time " + embeddedPerson.getReg_time());
            throw new Exception(new Throwable("person birthdate (" + embeddedPerson.getBirth_date() + ") is after reg time " + embeddedPerson.getReg_time()));
        }
        if (!isDateInRange2010to2100(embeddedPerson.getReg_time())) {
            log.info("person reg. time not in range 2010-2100! Current reg. time: " + embeddedPerson.getReg_time());
            throw new Exception(new Throwable("person reg. time not in range 2010-2100! Current reg. time: " + embeddedPerson.getReg_time()));
        }
        return true;
    }
}
