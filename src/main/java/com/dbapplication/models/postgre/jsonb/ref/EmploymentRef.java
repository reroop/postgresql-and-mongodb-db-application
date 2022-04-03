package com.dbapplication.models.postgre.jsonb.ref;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name="jsonb", typeClass = JsonBinaryType.class)
@Table(name="employment")
public class EmploymentRef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    @Column(name = "person_id", nullable = false)
    private Long personId;

    @Column(name = "occupation_code", nullable = false)
    private Integer occupationCode;

    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private EmploymentData data;

    public EmploymentRef(Long personId, Integer occupationCode, EmploymentData data) {
        this.personId = personId;
        this.occupationCode = occupationCode;
        this.data = data;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmploymentData {
        private LocalDateTime start_time;
        private LocalDateTime end_time;
    }
}
