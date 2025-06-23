package org.example.capstone.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.PolicyDto;
import org.example.capstone.entity.Policy;
import org.example.capstone.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolicyService {
  private final PolicyRepository policyRepository;
  private final ObjectMapper objectMapper;

  @Value("${rda.service-key}")
  private String apiKeyNm;

  public Page<PolicyDto> getAllPolicies(int page) {
    Pageable pageable = PageRequest.of(page, 8);
    
    return policyRepository.findAll(pageable).map(PolicyDto::from);
  }

  public String getPolicies() throws IOException {
    StringBuilder urlBuilder = new StringBuilder("https://www.youthcenter.go.kr/go/ythip/getPlcy");
    urlBuilder.append("?");
    urlBuilder.append("apiKeyNm=").append(URLEncoder.encode(apiKeyNm, "UTF-8"));
    urlBuilder.append("&zipCd=").append(URLEncoder.encode("47730", "UTF-8"));
    urlBuilder.append("&pageSize=").append(URLEncoder.encode("500", "UTF-8"));
    urlBuilder.append("&rtnType=").append(URLEncoder.encode("json", "UTF-8"));

    URL url = new URL(urlBuilder.toString());
    System.out.println(url);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    BufferedReader rd;
    if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
      rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    } else {
      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }

    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      sb.append(line);
    }

    rd.close();
    conn.disconnect();

    return sb.toString();
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void fetchAndSavePolicies() throws IOException {
    String json = getPolicies();

    JsonNode root = objectMapper.readTree(json);
    JsonNode policyList = root.path("result").path("youthPolicyList");

    List<PolicyDto> dtoList = objectMapper.readValue(
            policyList.toString(),
            new TypeReference<List<PolicyDto>>() {
            }
    );

    List<Policy> entities = dtoList.stream()
            .map(this::convertToEntity)
            .collect(Collectors.toList());

    policyRepository.saveAll(entities);
  }

  private Policy convertToEntity(PolicyDto dto) {
    Policy p = new Policy();
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
    return p;
  }

}
