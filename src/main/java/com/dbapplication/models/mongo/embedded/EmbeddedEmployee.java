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
@Document("tootaja")
public class EmbeddedEmployee {

    private Integer tootaja_seisundi_liik_kood;
    private String mentor_id;
    private List<EmbeddedEmployment> ametis_tootamine;

    @Override
    public String toString() {
        return "EmbeddedEmployee{" +
                "tootaja_seisundi_liik_kood=" + tootaja_seisundi_liik_kood +
                ", mentor_id='" + mentor_id + '\'' +
                ", ametis_tootamine=" + ametis_tootamine +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Document("tootaja")
    public static class EmbeddedEmployeeDbEntry {

        private Integer tootaja_seisundi_liik_kood;
        @Setter
        private ObjectId mentor_id;

        public EmbeddedEmployeeDbEntry(Integer tootaja_seisundi_liik_kood, String mentor_id) {
            this.tootaja_seisundi_liik_kood = tootaja_seisundi_liik_kood;
            this.mentor_id = new ObjectId(mentor_id);
        }

        public EmbeddedEmployeeDbEntry(Integer tootaja_seisundi_liik_kood) {
            this.tootaja_seisundi_liik_kood = tootaja_seisundi_liik_kood;
        }
    }
}
