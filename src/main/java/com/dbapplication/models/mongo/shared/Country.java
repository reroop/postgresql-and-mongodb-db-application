package com.dbapplication.models.mongo.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "riik")
public class Country {

    @Getter
    @Id
    private String _id;

    @Getter
    private String riik_kood;
    @Getter
    private String nimetus;

    public Country(String code, String name) {
        this.riik_kood = code;
        this.nimetus = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id='" + _id + '\'' +
                ", countryCode='" + riik_kood + '\'' +
                ", countryName='" + nimetus + '\'' +
                '}';
    }
}
