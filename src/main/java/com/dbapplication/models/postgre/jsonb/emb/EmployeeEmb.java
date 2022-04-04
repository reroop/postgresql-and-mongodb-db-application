package com.dbapplication.models.postgre.jsonb.emb;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name="jsonb", typeClass = JsonBinaryType.class)

public class EmployeeEmb {

    private Integer employee_status_type_code;
    private Long mentor_id;

    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private List<EmploymentEmb> employment;
}
