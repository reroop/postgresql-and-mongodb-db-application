package com.dbapplication.models.postgre;

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
@Table(name="employment")
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class EmploymentDto {
        private Employment employment;
    }
}


