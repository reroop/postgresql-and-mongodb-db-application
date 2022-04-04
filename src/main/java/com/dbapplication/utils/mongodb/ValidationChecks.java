package com.dbapplication.utils.mongodb;

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

    public static boolean isPostgreJsonRefPersonValid(PersonRef personRef) {
        LocalDateTime birthDateInLocalDateTime =  LocalDateTime.ofInstant(personRef.getData().getBirth_date().toInstant(), ZoneId.systemDefault());

        if (!isDateInRange1900to2100(birthDateInLocalDateTime)) {
            log.info("person, birthdate not in range 1900-2100");
            return false;
        }
        if (!isFirstDateBeforeSecondDate(birthDateInLocalDateTime, personRef.getData().getReg_time())) {
            log.info("person, birth date not before reg time");
            return false;
        }
        if (!isDateInRange2010to2100(personRef.getData().getReg_time())) {
            log.info("person, reg time not in rage 2010-2100");
            return false;
        }
        return true;
    }

    public static boolean isPostgreJsonEmbPersonValid(PersonEmb personEmb) {
        LocalDateTime birthDateInLocalDateTime =  LocalDateTime.ofInstant(personEmb.getData().getBirth_date().toInstant(), ZoneId.systemDefault());

        System.out.println(birthDateInLocalDateTime);

        if (!isDateInRange1900to2100(birthDateInLocalDateTime)) {
            log.info("person, birthdate not in range 1900-2100");
            return false;
        }
        if (!isFirstDateBeforeSecondDate(birthDateInLocalDateTime, personEmb.getData().getReg_time())) {
            log.info("person, birth date not before reg time");
            return false;
        }
        if (!isDateInRange2010to2100(personEmb.getData().getReg_time())) {
            log.info("person, reg time not in rage 2010-2100");
            return false;
        }
        return true;
    }


    public static boolean isMongoPersonInfoValid(Person person) {
        if (!isDateInRange1900to2100(person.getBirth_date())) {
            log.info("person, birthdate not in range 1900-2100");
            return false;
        }
        if (person.getGiven_name() == null && person.getSurname() == null ||
                Objects.equals(person.getGiven_name(), "") && Objects.equals(person.getSurname(), "")) {
            log.info("person, given name or surname must be set");
            return false;
        }
        if (!isFirstDateBeforeSecondDate(person.getBirth_date(), person.getReg_time())) {
            log.info("person, birth date not before reg time");
            return false;
        }
        if (!isDateInRange2010to2100(person.getReg_time())) {
            log.info("person, reg time not in rage 2010-2100");
            return false;
        }
        return true;
    }


    public static boolean isMongoEmbeddedPersonInfoValid(EmbeddedPerson embeddedPerson) {
        if (!isDateInRange1900to2100(embeddedPerson.getBirth_date())) {
            log.info("person, birthdate not in range 1900-2100");
            return false;
        }
        if (embeddedPerson.getGiven_name() == null && embeddedPerson.getSurname() == null ||
                Objects.equals(embeddedPerson.getGiven_name(), "") && Objects.equals(embeddedPerson.getSurname(), "")) {
            log.info("person, given name or surname must be set");
            return false;
        }
        if (!isFirstDateBeforeSecondDate(embeddedPerson.getBirth_date(), embeddedPerson.getReg_time())) {
            log.info("person, birth date not before reg time");
            return false;
        }
        if (!isDateInRange2010to2100(embeddedPerson.getReg_time())) {
            log.info("person, reg time not in range 2010-2100");
            return false;
        }
        return true;
    }
}
