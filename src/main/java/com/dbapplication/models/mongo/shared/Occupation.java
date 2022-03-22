package com.dbapplication.models.mongo.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "amet")
public class Occupation {

    @Getter
    @Id
    private String _id;

    @Getter
    private Integer amet_kood;
    @Getter
    private String nimetus;
    @Getter
    private String kirjeldus;

    public Occupation(Integer code, String name) {
        this.amet_kood = code;
        this.nimetus = name;
    }

    public Occupation(Integer code, String name, String description) {
        this.amet_kood = code;
        this.nimetus = name;
        this.kirjeldus = description;
    }

    @Override
    public String toString() {
        return "Occupation{" +
                "id='" + _id + '\'' +
                ", amet_kood='" + amet_kood + '\'' +
                ", nimetus='" + nimetus + '\'' +
                ", kirjeldus='" + kirjeldus + '\'' +
                '}';
    }
}
