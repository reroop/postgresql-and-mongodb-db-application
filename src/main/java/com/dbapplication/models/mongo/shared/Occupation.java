package com.dbapplication.models.mongo.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "occupation")
public class Occupation {

    @Getter
    @Id
    private String _id;

    @Getter
    private Integer occupation_code;
    @Getter
    private String name;
    @Getter
    private String description;

    public Occupation(Integer code, String name) {
        this.occupation_code = code;
        this.name = name;
    }

    public Occupation(Integer code, String name, String description) {
        this.occupation_code = code;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Occupation{" +
                "_id='" + _id + '\'' +
                ", occupation_code=" + occupation_code +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class OccupationDto {
        private Occupation occupation;
    }
}
