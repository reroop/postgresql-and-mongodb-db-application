package com.dbapplication.models.postgre.traditional;

import com.dbapplication.models.postgre.jsonb.emb.EmploymentEmb;
import com.dbapplication.models.postgre.jsonb.ref.EmploymentRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(Employment.EmploymentCompositeKey.class)
@Table(name = "employment")
public class Employment {

    @Id
    @Column(name = "person_id", nullable = false)
    private Long personId;

    @Id
    @Column(name = "occupation_code", nullable = false)
    private Integer occupationCode;

    @Id
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Override
    public String toString() {
        return "Employment{" +
                "personId=" + personId +
                ", occupationCode=" + occupationCode +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class EmploymentCompositeKey implements Serializable {
        private Long personId;
        private Integer occupationCode;
        private LocalDateTime startTime;

    }

    //--------------
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class FrontEmployment {
        private String person_id;
        private Integer occupation_code;
        private LocalDateTime start_time;
        private LocalDateTime end_time;

        @Override
        public String toString() {
            return "FrontEmployment{" +
                    "person_id='" + person_id + '\'' +
                    ", occupation_code=" + occupation_code +
                    ", start_time=" + start_time +
                    ", end_time=" + end_time +
                    '}';
        }
    }


    //--------------

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class EmploymentDto {
        private FrontEmployment employment;

        public Employment createPostgreEmployment() {
            Employment employment = new Employment(
                    Long.valueOf(this.employment.getPerson_id()),
                    this.employment.getOccupation_code(),
                    this.employment.getStart_time(),
                    this.employment.getEnd_time());

            //System.out.println("createdpostgreemployment");
            return employment;
        }

        public EmploymentRef createPostgreRefEmployment() {
            EmploymentRef employmentRef = new EmploymentRef(
                    Long.valueOf(this.employment.getPerson_id()),
                    this.employment.getOccupation_code(),
                    new EmploymentRef.EmploymentData(employment.getStart_time(), employment.getEnd_time())
            );
            return employmentRef;
        }

        public EmploymentEmb createPostgreEmbEmployment() {
            EmploymentEmb employmentEmb = new EmploymentEmb(
                    this.employment.getOccupation_code(),
                    this.employment.getStart_time(),
                    this.employment.getEnd_time()
            );
            return employmentEmb;
        }

        @Override
        public String toString() {
            return "EmploymentDto{" +
                    "employment=" + employment +
                    '}';
        }
    }
}


