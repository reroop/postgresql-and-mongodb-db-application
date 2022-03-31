package com.dbapplication.models.mongo.reference;

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
@Document(collection = "person")
public class Person {

    @Id
    private String _id;
    private String nat_id_code;
    private String country_code;
    private String e_mail;
    private LocalDateTime birth_date;
    private LocalDateTime reg_time;
    private String given_name;
    private String surname;
    private String address;

    public Person(String nat_id_code, String country_code, String e_mail, LocalDateTime birth_date, LocalDateTime reg_time, String given_name, String surname, String address) {
        this.nat_id_code = nat_id_code;
        this.country_code = country_code;
        this.e_mail = e_mail;
        this.birth_date = birth_date;
        this.reg_time = reg_time;
        this.given_name = given_name;
        this.surname = surname;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "_id='" + _id + '\'' +
                ", nat_id_code='" + nat_id_code + '\'' +
                ", country_code='" + country_code + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", birth_date=" + birth_date +
                ", reg_time=" + reg_time +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PersonDto {
        private Person person;
    }
}
