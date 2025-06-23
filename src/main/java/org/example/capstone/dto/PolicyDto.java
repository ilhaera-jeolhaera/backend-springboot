package org.example.capstone.dto;

import lombok.Builder;
import lombok.Data;
import org.example.capstone.entity.Policy;
import org.example.capstone.entity.PostImage;

import java.util.stream.Collectors;

@Data @Builder
public class PolicyDto {
  private String plcyNo;
  private String plcyNm;
  private String plcyKywdNm;
  private String plcyExplnCn;
  private String lclsfNm;
  private String mclsfNm;
  private String plcySprtCn;
  private String plcyAplyMthdCn;
  private String srngMthdCn;
  private String aplyUrlAddr;
  private String aplyYmd;
  private String bizPrdBgngYmd;
  private String bizPrdEndYmd;
  private String etcMttrCn;
  private String sprvsnInstCdNm;
  private String operInstCdNm;
  private String sprtTrgtMinAge;
  private String sprtTrgtMaxAge;

  public static PolicyDto from(Policy policy) {
    return PolicyDto.builder()
            .plcyNo(policy.getPlcyNo())
            .plcyNm(policy.getPlcyNm())
            .plcyKywdNm(policy.getPlcyKywdNm())
            .plcyExplnCn(policy.getPlcyExplnCn())
            .lclsfNm(policy.getLclsfNm())
            .mclsfNm(policy.getMclsfNm())
            .plcySprtCn(policy.getPlcySprtCn())
            .plcyAplyMthdCn(policy.getPlcyAplyMthdCn())
            .srngMthdCn(policy.getSrngMthdCn())
            .aplyUrlAddr(policy.getAplyUrlAddr())
            .aplyYmd(policy.getAplyYmd())
            .bizPrdBgngYmd(policy.getBizPrdBgngYmd())
            .bizPrdEndYmd(policy.getBizPrdEndYmd())
            .etcMttrCn(policy.getEtcMttrCn())
            .sprvsnInstCdNm(policy.getSprvsnInstCdNm())
            .operInstCdNm(policy.getOperInstCdNm())
            .sprtTrgtMinAge(policy.getSprtTrgtMinAge())
            .sprtTrgtMaxAge(policy.getSprtTrgtMaxAge())
            .build();
  }
}
