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
@Document(collection = "isik")
public class EmbeddedPerson {

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

    @Getter
    @Setter
    private EmbeddedUserAccount kasutajakonto;

    @Getter
    @Setter
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
