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
@Getter
@Setter
@Document(collection = "employment")
public class Employment {

    @Id
    private String _id;
    private String person_id;
    private Integer occupation_code;
    private LocalDateTime start_time;
    private LocalDateTime end_time;

    @Override
    public String toString() {
        return "Employment{" +
                "_id='" + _id + '\'' +
                ", person_id='" + person_id + '\'' +
                ", occupation_code=" + occupation_code +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
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
    @Getter
    @Setter
    @Document(collection = "employment")
    public static class EmploymentDbEntry {

        @Id
        private String _id;
        private ObjectId person_id;
        private Integer occupation_code;
        private LocalDateTime start_time;
        private LocalDateTime end_time;

        public EmploymentDbEntry(String personId, Integer occupationCode, LocalDateTime startTime) {
            this.person_id = new ObjectId(personId);
            this.occupation_code = occupationCode;
            this.start_time = startTime;
        }
    }
}
