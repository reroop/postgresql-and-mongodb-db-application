package com.dbapplication.models.mongo.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "isik")
public class Person {

    @Id
    private String _id;
    private String isikukood;
    private String riik_kood;
    private String e_meil;
    private LocalDateTime synni_kp;
    private LocalDateTime reg_aeg;
    private String eesnimi;
    private String perenimi;
    private String elukoht;

    public Person(String isikukood, String riik_kood, String e_meil, LocalDateTime synni_kp, LocalDateTime reg_aeg, String eesnimi, String perenimi, String elukoht) {
        this.isikukood = isikukood;
        this.riik_kood = riik_kood;
        this.e_meil = e_meil;
        this.synni_kp = synni_kp;
        this.reg_aeg = reg_aeg;
        this.eesnimi = eesnimi;
        this.perenimi = perenimi;
        this.elukoht = elukoht;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PersonDto {
        private Person person;
    }

    @Override
    public String toString() {
        return "Person{" +
                "_id='" + _id + '\'' +
                ", isikukood='" + isikukood + '\'' +
                ", riik_kood='" + riik_kood + '\'' +
                ", e_meil='" + e_meil + '\'' +
                ", synni_kp=" + synni_kp +
                ", reg_aeg=" + reg_aeg +
                ", eesnimi='" + eesnimi + '\'' +
                ", perenimi='" + perenimi + '\'' +
                ", elukoht='" + elukoht + '\'' +
                '}';
    }
}
