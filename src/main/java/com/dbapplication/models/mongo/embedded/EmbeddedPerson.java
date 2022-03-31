package com.dbapplication.models.mongo.embedded;

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
public class EmbeddedPerson {

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
    private EmbeddedUserAccount kasutajakonto;
    private EmbeddedEmployee tootaja;

    @Override
    public String toString() {
        return "EmbeddedPerson{" +
                "_id='" + _id + '\'' +
                ", isikukood='" + isikukood + '\'' +
                ", riik_kood='" + riik_kood + '\'' +
                ", e_meil='" + e_meil + '\'' +
                ", synni_kp=" + synni_kp +
                ", reg_aeg=" + reg_aeg +
                ", eesnimi='" + eesnimi + '\'' +
                ", perenimi='" + perenimi + '\'' +
                ", elukoht='" + elukoht + '\'' +
                ", kasutajakonto=" + kasutajakonto +
                ", tootaja=" + tootaja +
                '}';
    }
}
