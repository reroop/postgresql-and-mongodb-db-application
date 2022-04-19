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
@Table(name="person_status_type")
public class PersonStatusTypePostgreJsonCommon {

    @Id
    private Integer person_status_type_code;

    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private PersonStatusTypeData data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonStatusTypeData {
        private String name;
        private String description;
    }
}
