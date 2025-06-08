package org.example.capstone.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;

@Service
public class PolicyService {

  @Value("${rda.service-key}")
  private String serviceKey;

  public String getPolicies() throws IOException {
    String type = "json";
    LocalDate start = LocalDate.now();
    LocalDate end = start.plusDays(14);

    StringBuilder urlBuilder = new StringBuilder("https://www.rda.go.kr/young/api/eduList");
    StringBuilder parameter = new StringBuilder();
    parameter.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(serviceKey, "UTF-8"));
    parameter.append("&" + URLEncoder.encode("typeDv", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8"));
    parameter.append("&" + URLEncoder.encode("sd", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(start), "UTF-8"));
    parameter.append("&" + URLEncoder.encode("ed", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(end), "UTF-8"));
    parameter.append("&" + URLEncoder.encode("rowCnt", "UTF-8") + "=" + URLEncoder.encode("5", "UTF-8"));
    parameter.append("&" + URLEncoder.encode("search_area1", "UTF-8") + "=" + URLEncoder.encode("37320", "UTF-8"));

    URL url = new URL(urlBuilder.toString() + parameter.toString());
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/" + type);

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
