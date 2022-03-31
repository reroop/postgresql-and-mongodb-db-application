package com.dbapplication.models.mongo.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "employee")
public class Employee {

    @Id
    private String _id;
    private String person_id;
    private Integer employee_status_type_code;
    private String mentor_id;

    @Override
    public String toString() {
        return "Employee{" +
                "_id='" + _id + '\'' +
                ", person_id='" + person_id + '\'' +
                ", employee_status_type_code=" + employee_status_type_code +
                ", mentor_id='" + mentor_id + '\'' +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class EmployeeDto {
        private Employee employee;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Document(collection = "employee")
    public static class EmployeeDbEntry {

        @Id
        private String _id;

        private ObjectId person_id;
        private Integer employee_status_type_code;
        private ObjectId mentor_id;

        public EmployeeDbEntry(String person_id, Integer employee_status_type_code) {
            this.person_id = new ObjectId(person_id);
            this.employee_status_type_code = employee_status_type_code;
        }
    }
}
