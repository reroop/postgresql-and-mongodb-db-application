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
@Document(collection = "country")
public class Country {

    @Id
    private String _id;

    private String country_code;
    private String name;

    @Override
    public String toString() {
        return "Country{" +
                "_id='" + _id + '\'' +
                ", country_code='" + country_code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CountryDto {
        private Country country;
    }
}
