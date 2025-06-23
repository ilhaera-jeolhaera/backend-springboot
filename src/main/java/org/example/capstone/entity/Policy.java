package org.example.capstone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Policy {
  @Id
  private String plcyNo;

  private String plcyNm;
  private String plcyKywdNm;
  @Column(columnDefinition = "TEXT")
  private String plcyExplnCn;
  private String lclsfNm;
  private String mclsfNm;
  private String plcySprtCn;
  @Column(columnDefinition = "TEXT")
  private String plcyAplyMthdCn;
  @Column(columnDefinition = "TEXT")
  private String srngMthdCn;
  @Column(columnDefinition = "TEXT")
  private String aplyUrlAddr;
  private String aplyYmd;
  private String bizPrdBgngYmd;
  private String bizPrdEndYmd;
  @Column(columnDefinition = "TEXT")
  private String etcMttrCn;
  private String sprvsnInstCdNm;
  private String operInstCdNm;
  private String sprtTrgtMinAge;
  private String sprtTrgtMaxAge;
}
