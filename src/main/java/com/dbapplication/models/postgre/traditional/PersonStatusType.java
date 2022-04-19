package com.dbapplication.models.postgre.traditional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="person_status_type")
public class PersonStatusType {

    @Id
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
