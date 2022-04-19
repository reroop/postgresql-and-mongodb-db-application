package com.dbapplication.models.mongo.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "person_status_type")
public class PersonStatusType {

    @Id
    private String _id;

    private Integer person_status_type_code;
    private String name;
    private String description;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PersonStatusTypeDto {
        private PersonStatusType personStatusType;
    }
}
