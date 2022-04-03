package com.dbapplication.models.postgre.jsonb.common;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name="jsonb", typeClass = JsonBinaryType.class)
@Table(name="employee_status_type")
public class EmployeeStatusTypePostgreJsonCommon {

    @Id
    private Integer employee_status_type_code;

    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private EmployeeStatusTypeData data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeStatusTypeData {

        private String name;
        private String description;
    }
}
