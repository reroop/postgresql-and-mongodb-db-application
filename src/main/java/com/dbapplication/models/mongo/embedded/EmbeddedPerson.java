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
@Getter
@Setter
@Document(collection = "person")
public class EmbeddedPerson {

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
    private String tel_nr;
    private EmbeddedEmployee employee;

    @Override
    public String toString() {
        return "EmbeddedPerson{" +
                "_id='" + _id + '\'' +
                ", nat_id_code='" + nat_id_code + '\'' +
                ", country_code='" + country_code + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", birth_date=" + birth_date +
                ", reg_time=" + reg_time +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", tel_nr='" + tel_nr + '\'' +
                ", employee=" + employee +
                '}';
    }
}
