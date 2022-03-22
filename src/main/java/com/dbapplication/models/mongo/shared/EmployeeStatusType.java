package com.dbapplication.models.mongo.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tootaja_seisundi_liik")
public class EmployeeStatusType {

    @Getter
    @Id
    private String _id;

    @Getter
    private Integer tootaja_seisundi_liik_kood;
    @Getter
    private String nimetus;
    @Getter
    private String kirjeldus;

    public EmployeeStatusType(Integer code, String name, String description) {
        this.tootaja_seisundi_liik_kood = code;
        this.nimetus = name;
        this.kirjeldus = description;
    }


    public EmployeeStatusType(Integer code, String name) {
        this.tootaja_seisundi_liik_kood = code;
        this.nimetus = name;
    }

    @Override
    public String toString() {
        return "EmployeeStatusType{" +
                "id='" + _id + '\'' +
                ", tootaja_seisundi_liik_kood=" + tootaja_seisundi_liik_kood +
                ", nimetus='" + nimetus + '\'' +
                ", kirjeldus='" + kirjeldus + '\'' +
                '}';
    }
}
