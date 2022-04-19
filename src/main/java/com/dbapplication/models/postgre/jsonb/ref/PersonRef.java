package com.dbapplication.models.postgre.jsonb.ref;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name="jsonb", typeClass = JsonBinaryType.class)
@Table(name="person")
public class PersonRef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    private String country_code;
    private Integer person_status_type_code;

    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private PersonData data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonData {
        private String nat_id_code;
        private String e_mail;
        private Date birth_date;
        private LocalDateTime reg_time;
        private String given_name;
        private String surname;
        private String address;
        private String tel_nr;
    }
}
