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
@Document(collection = "isik")
public class Person {

    @Getter
    @Id
    private String _id;

    @Getter
    private String isikukood;

    @Getter
    private String riik_kood;

    @Getter
    private String e_meil;

    @Getter
    private LocalDateTime synni_kp;

    @Getter
    @Setter
    private LocalDateTime reg_aeg;

    @Getter
    private String eesnimi;

    @Getter
    private String perenimi;

    @Getter
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
