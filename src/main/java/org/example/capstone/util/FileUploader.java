package org.example.capstone.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUploader {
  @Value("${file.upload-dir:D:/spring/capstone/src/main/resources/static/uploads}")
  private String uploadDir;

  @Value("${server.base-url:http://localhost:8080/}")
  private String baseUrl;

  public String upload(MultipartFile file) throws IOException {
    File directory = new File(uploadDir);

    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
    File destination = new File(directory, fileName);
    file.transferTo(destination);

    return baseUrl + "/images/" + fileName;
  }

  public void delete(String imageUrl) throws IOException {
    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    File file = new File(uploadDir, fileName);

    if (file.exists()) {
      if(!file.delete()) {
        throw new IOException("파일 삭제 실패 : " + file.getAbsolutePath());
      }
    }

  }
}
