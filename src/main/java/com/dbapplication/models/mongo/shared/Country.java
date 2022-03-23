package com.dbapplication.models.mongo.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "riik")
public class Country {

    @Getter
    @Id
    @Setter
    private String _id;

    @Getter
    @Setter
    private String riik_kood;

    @Getter
    @Setter
    private String nimetus;

    public Country(String code, String name) {
        this.riik_kood = code;
        this.nimetus = name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CountryDto {
        private Country country;
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
