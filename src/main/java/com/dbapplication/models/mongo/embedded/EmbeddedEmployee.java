package com.dbapplication.models.mongo.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("employee")
public class EmbeddedEmployee {

    private Integer employee_status_type_code;
    private String mentor_id;
    private List<EmbeddedEmployment> employment;

    @Override
    public String toString() {
        return "EmbeddedEmployee{" +
                "employee_status_type_code=" + employee_status_type_code +
                ", mentor_id='" + mentor_id + '\'' +
                ", employment=" + employment +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Document("employee")
    public static class EmbeddedEmployeeDbEntry {

        private Integer employee_status_type_code;
        private ObjectId mentor_id;

        public EmbeddedEmployeeDbEntry(Integer employee_status_type_code) {
            this.employee_status_type_code = employee_status_type_code;
        }
    }
}
