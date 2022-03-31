package com.dbapplication.models.postgre;

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
@Table(name="occupation")
public class Occupation {

    @Id
    private Integer occupation_code;

    private String name;
    private String description;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class OccupationDto {
        private Occupation occupation;
    }
}
