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
@Document(collection = "employee_status_type")
public class EmployeeStatusType {

    @Id
    private String _id;

    private Integer employee_status_type_code;
    private String name;
    private String description;


    @Override
    public String toString() {
        return "EmployeeStatusType{" +
                "_id='" + _id + '\'' +
                ", employee_status_type_code=" + employee_status_type_code +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class EmployeeStatusTypeDto {
        private EmployeeStatusType employeeStatusType;
    }

}
