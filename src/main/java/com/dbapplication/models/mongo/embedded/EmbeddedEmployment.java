package com.dbapplication.models.mongo.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Document("ametis_tootamine")
public class EmbeddedEmployment {

    @Getter
    private Integer amet_kood;

    @Getter
    private LocalDateTime alguse_aeg;

    @Getter
    @Setter
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
