package com.dbapplication.models.mongo.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ametis_tootamine")
public class Employment {

    @Getter
    @Id
    private String _id;

    @Getter
    private String isik_id;

    @Getter
    private Integer amet_kood;

    @Getter
    private LocalDateTime alguse_aeg;

    @Getter
    private LocalDateTime lopu_aeg;

    @Override
    public String toString() {
        return "EmployeeInOccupation{" +
                "_id='" + _id + '\'' +
                ", isik_id='" + isik_id + '\'' +
                ", amet_kood=" + amet_kood +
                ", alguse_aeg=" + alguse_aeg +
                ", lopu_aeg=" + lopu_aeg +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class EmploymentDto {
        private Employment employment;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Document(collection = "ametis_tootamine")
    public static class EmploymentDbEntry {

        @Getter
        @Id
        private String _id;

        @Getter
        private ObjectId isik_id;

        @Getter
        private Integer amet_kood;

        @Getter
        private LocalDateTime alguse_aeg;

        @Getter
        private LocalDateTime lopu_aeg;

        public EmploymentDbEntry(String personId, Integer occupationCode, LocalDateTime startTime) {
            this.isik_id = new ObjectId(personId);
            this.amet_kood = occupationCode;
            this.alguse_aeg = startTime;
        }
    }
}
