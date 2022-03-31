package com.dbapplication.models.mongo.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("employment")
public class EmbeddedEmployment {

    private Integer occupation_code;
    private LocalDateTime start_time;
    private LocalDateTime end_time;

    @Override
    public String toString() {
        return "EmbeddedEmployment{" +
                "occupation_code=" + occupation_code +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                '}';
    }
}
