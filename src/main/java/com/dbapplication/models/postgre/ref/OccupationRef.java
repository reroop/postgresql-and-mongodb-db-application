package com.dbapplication.models.postgre.ref;

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
@Table(name="occupation")
public class OccupationRef {

    @Id
    private Integer occupation_code;

    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private OccupationData data;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OccupationData {

        private String name;
        private String description;
    }
}
