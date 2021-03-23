package kr.co.metasoft.ito.api.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_career")
@EntityListeners(value = {AuditingEntityListener.class})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CareerEntity {
    @Id
    @Column(name = "person_career_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personCareerId;

    @Column(name = "name")
    private String name;

    @Column(name = "start_period")
    private LocalDate startPeriod;

    @Column(name = "end_period")
    private LocalDate endPeriod;

    @Column(name = "position")
    private String position;

    @Column(name = "task")
    private String task;

    @Column(name = "pay")
    private String pay;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_dt", columnDefinition = "datetime", nullable = false)
    private LocalDateTime createDt;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modify_dt", columnDefinition = "datetime", nullable = false)
    private LocalDateTime modifyDt;
}
