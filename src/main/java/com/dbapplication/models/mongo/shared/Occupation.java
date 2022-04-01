package com.dbapplication.models.mongo.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "occupation")
public class Occupation {

    @Id
    private String _id;

    private Integer occupation_code;
    private String name;
    private String description;

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
