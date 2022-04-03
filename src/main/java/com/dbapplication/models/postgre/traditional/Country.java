package com.dbapplication.models.postgre.traditional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="country")
public class Country {

    @Id
    private String country_code;

    private String name;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CountryDto {
        private Country country;
    }
}
