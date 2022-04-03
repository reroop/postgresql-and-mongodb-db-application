package com.dbapplication.models.postgre.jsonb.common;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name="jsonb", typeClass = JsonBinaryType.class)
@Table(name="country")
public class CountryPostgreJsonCommon {

    @Id
    private String country_code;

    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private CountryData data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CountryData implements Serializable {

        private String name;
    }
}
