package org.example.capstone.batch.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.PolicyDto;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PolicyItemReader implements ItemReader<PolicyDto> {

  private final ObjectMapper objectMapper;
  @Value("${rda.service-key}")
  private String apiKeyNm;

  private IteratorItemReader<PolicyDto> delegate;

  @Override
  public PolicyDto read() throws Exception {
    if (delegate == null) {
      List<PolicyDto> dtoList = loadPolicyList();
      delegate = new IteratorItemReader<>(dtoList);
    }
    return delegate.read();
  }

  private List<PolicyDto> loadPolicyList() throws IOException {
    String json = getPolicies();
    JsonNode root = objectMapper.readTree(json);
    JsonNode policyList = root.path("result").path("youthPolicyList");

    return objectMapper.readValue(
            policyList.toString(),
            new TypeReference<List<PolicyDto>>() {}
    );
  }

  private String getPolicies() throws IOException {
    StringBuilder urlBuilder = new StringBuilder("https://www.youthcenter.go.kr/go/ythip/getPlcy");
    urlBuilder.append("?");
    urlBuilder.append("apiKeyNm=").append(URLEncoder.encode(apiKeyNm, "UTF-8"));
    urlBuilder.append("&zipCd=").append(URLEncoder.encode("47730", "UTF-8"));
    urlBuilder.append("&pageSize=").append(URLEncoder.encode("500", "UTF-8"));
    urlBuilder.append("&rtnType=").append(URLEncoder.encode("json", "UTF-8"));

    URL url = new URL(urlBuilder.toString());
    System.out.println("Request URL: " + url);
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
}
