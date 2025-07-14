package org.example.capstone.batch.processor;

import org.example.capstone.dto.PolicyDto;
import org.example.capstone.entity.Policy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class PolicyItemProcessor implements ItemProcessor<PolicyDto, Policy> {

  @Override
  public Policy process(PolicyDto dto) {
    Policy p = new Policy();

    boolean isEnd = false;
    try {
      if (dto.getAplyYmd() != null && dto.getAplyYmd().contains("~")) {
        String endStr = dto.getAplyYmd().split("~")[1].trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate endDate = LocalDate.parse(endStr, formatter);
        isEnd = LocalDate.now().isAfter(endDate);
      }
    } catch (Exception e) {
      isEnd = false;
    }

    p.setPlcyNo(dto.getPlcyNo());
    p.setPlcyNm(dto.getPlcyNm());
    p.setPlcyKywdNm(dto.getPlcyKywdNm());
    p.setPlcyExplnCn(dto.getPlcyExplnCn());
    p.setLclsfNm(dto.getLclsfNm());
    p.setMclsfNm(dto.getMclsfNm());
    p.setPlcySprtCn(dto.getPlcySprtCn());
    p.setPlcyAplyMthdCn(dto.getPlcyAplyMthdCn());
    p.setSrngMthdCn(dto.getSrngMthdCn());
    p.setAplyUrlAddr(dto.getAplyUrlAddr());
    p.setAplyYmd(dto.getAplyYmd());
    p.setBizPrdBgngYmd(dto.getBizPrdBgngYmd());
    p.setBizPrdEndYmd(dto.getBizPrdEndYmd());
    p.setEtcMttrCn(dto.getEtcMttrCn());
    p.setSprvsnInstCdNm(dto.getSprvsnInstCdNm());
    p.setOperInstCdNm(dto.getOperInstCdNm());
    p.setSprtTrgtMinAge(dto.getSprtTrgtMinAge());
    p.setSprtTrgtMaxAge(dto.getSprtTrgtMaxAge());
    p.setEnd(isEnd);
    return p;
  }
}
