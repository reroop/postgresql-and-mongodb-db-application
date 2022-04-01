package com.dbapplication.models.postgre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="employee_status_type")
public class EmployeeStatusType {

    @Id
    private Integer employee_status_type_code;

    private String name;
    private String description;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class EmployeeStatusTypeDto {
        private EmployeeStatusType employeeStatusType;
    }
}
