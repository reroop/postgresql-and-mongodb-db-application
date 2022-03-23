package com.dbapplication.models.mongo.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document("kasutajakonto")
public class EmbeddedUserAccount {

    @Getter
    private String parool;

    @Getter
    private Boolean on_aktiivne;

    @Override
    public String toString() {
        return "EmbeddedUserAccount{" +
                "parool='" + parool + '\'' +
                ", on_aktiivne=" + on_aktiivne +
                '}';
    }
}
