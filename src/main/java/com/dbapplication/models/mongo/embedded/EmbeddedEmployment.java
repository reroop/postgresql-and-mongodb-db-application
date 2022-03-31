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
@Document("ametis_tootamine")
public class EmbeddedEmployment {

    private Integer amet_kood;
    private LocalDateTime alguse_aeg;
    private LocalDateTime lopu_aeg;

    @Override
    public String toString() {
        return "EmbeddedEmployment{" +
                "amet_kood=" + amet_kood +
                ", alguse_aeg=" + alguse_aeg +
                ", lopu_aeg=" + lopu_aeg +
                '}';
    }
}
