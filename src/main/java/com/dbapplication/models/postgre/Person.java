package com.dbapplication.models.postgre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    private String nat_id_code;
    private String country_code;
    private String e_mail;
    private Date birth_date;
    private LocalDateTime reg_time = LocalDateTime.now();
    private String given_name;
    private String surname;
    private String address;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PersonDto {
        private Person person;
    }
}
