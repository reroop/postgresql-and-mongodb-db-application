package com.dbapplication.models.postgre.traditional;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
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
    @Data
    public static class PersonDto {
        private Person person;
    }
}
