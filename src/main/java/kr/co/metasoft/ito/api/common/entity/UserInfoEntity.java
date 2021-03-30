package kr.co.metasoft.ito.api.common.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.co.metasoft.ito.common.validation.group.CreateValidationGroup;
import kr.co.metasoft.ito.common.validation.group.ModifyValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name = "`tb_userinfo`")
@EntityListeners (value = {AuditingEntityListener.class})
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoEntity {



  @Null (groups = {CreateValidationGroup.class})
  @NotNull (groups = {ModifyValidationGroup.class})
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column (name = "`id`", columnDefinition = "bigint(20)")
  private Long id;


  @Column(name = "`name`", columnDefinition = "varchar(100)", nullable = true)
  private String name;

  @Column(name = "`number`", columnDefinition = "varchar(100)", nullable = true)
  private String number;

  @Column(name = "`job_type`", columnDefinition = "varchar(100)", nullable = true)
  private String jobType;

  @Column(name = "`skill`", columnDefinition = "varchar(100)", nullable = true)
  private String skill;


    /*
     * @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
     *
     * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     *
     * @Column(name = "'birth_date'", columnDefinition = "datetime", nullable
     * =false, updatable = false) private LocalDateTime birthDate;
     */

  @Column(name = "`birth_date`")
  private LocalDate birthDate;


  @Column (name = "`career`", columnDefinition = "varchar(100)", nullable = true)
  private String career;

  @Column(name = "`pay`", columnDefinition = "varchar(100)", nullable = true)
  private String pay;

  @Column(name = "`input_status`", columnDefinition = "varchar(100)", nullable = true)
  private String inputStatus;


    /*
     * @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
     *
     * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     *
     * @Column(name = "'workable_day'", columnDefinition = "datetime", nullable
     * =false, updatable = false) private LocalDateTime workableDay;
     */

  @Column(name = "`workable_day`")
  private LocalDate workableDay;


  @Column(name = "`address`")
  private String address;

}
