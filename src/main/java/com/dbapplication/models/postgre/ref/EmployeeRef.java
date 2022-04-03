package com.dbapplication.models.postgre.ref;

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
@Table(name="employee")
public class EmployeeRef {

    @Id
    private Long person_id;

    private Long mentor_id;
    private Integer employee_status_type_code;
}
