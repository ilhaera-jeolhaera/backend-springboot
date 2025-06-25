package org.example.capstone.dto;

import lombok.Builder;
import lombok.Data;
import org.example.capstone.entity.Policy;
import org.example.capstone.entity.PostImage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
  private boolean isEnd;

  public static PolicyDto from(Policy policy) {
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    String aplyYmd = policy.getAplyYmd();
    String[] dateRange = aplyYmd.split("~");

    LocalDate endDate = null;
    if (dateRange.length == 2) {
      String endDateStr = dateRange[1].trim();
      endDate = LocalDate.parse(endDateStr, formatter);
    }

    boolean isEnd = (endDate != null) && today.isAfter(endDate);

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
            .isEnd(isEnd)
            .build();
  }
}
