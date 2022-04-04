package com.dbapplication.models.postgre.jsonb.emb;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name="jsonb", typeClass = JsonBinaryType.class)
public class EmploymentEmb {

    private Integer occupation_code;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
}
