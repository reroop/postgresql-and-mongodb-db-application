package com.dbapplication.models.mongo.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employee_status_type")
public class EmployeeStatusType {

    @Getter
    @Id
    private String _id;

    @Getter
    private Integer employee_status_type_code;
    @Getter
    private String name;
    @Getter
    private String description;

    public EmployeeStatusType(Integer code, String name, String description) {
        this.employee_status_type_code = code;
        this.name = name;
        this.description = description;
    }


    public EmployeeStatusType(Integer code, String name) {
        this.employee_status_type_code = code;
        this.name = name;
    }

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
