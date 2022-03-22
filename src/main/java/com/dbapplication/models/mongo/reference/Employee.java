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
@Document(collection = "tootaja")
public class Employee {

    @Getter
    @Id
    private String _id;

    @Getter
    private String isik_id;

    @Getter
    private Integer tootaja_seisundi_liik_kood;

    @Getter
    private String mentor_id;

    @Override
    public String toString() {
        return "Employee{" +
                "_id='" + _id + '\'' +
                ", isik_id='" + isik_id + '\'' +
                ", tootaja_seisundi_liik_kood=" + tootaja_seisundi_liik_kood +
                ", mentor_id='" + mentor_id + '\'' +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Document(collection = "tootaja")
    public static class EmployeeDbEntry {

        @Getter
        @Id
        private String _id;

        @Getter
        private ObjectId isik_id;

        @Getter
        @Setter
        private Integer tootaja_seisundi_liik_kood;

        @Getter
        @Setter
        private ObjectId mentor_id;

        public EmployeeDbEntry(String personId, Integer employeeStatusCode, String mentorId) {
            this.isik_id = new ObjectId(personId);
            this.tootaja_seisundi_liik_kood = employeeStatusCode;
            this.mentor_id = new ObjectId(mentorId);
        }

        public EmployeeDbEntry(String isik_id, Integer tootaja_seisundi_liik_kood) {
            this.isik_id = new ObjectId(isik_id);
            this.tootaja_seisundi_liik_kood = tootaja_seisundi_liik_kood;
        }
    }
}