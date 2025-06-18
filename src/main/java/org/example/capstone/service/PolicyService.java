package org.example.capstone.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class PolicyService {

  @Value("${rda.service-key}")
  private String apiKeyNm;

  public String getPolicies() throws IOException {
    StringBuilder urlBuilder = new StringBuilder("https://www.youthcenter.go.kr/go/ythip/getPlcy");
    urlBuilder.append("?");
    urlBuilder.append("apiKeyNm=").append(URLEncoder.encode(apiKeyNm, "UTF-8"));
    urlBuilder.append("&zipCd=").append(URLEncoder.encode("47730", "UTF-8"));
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
}
