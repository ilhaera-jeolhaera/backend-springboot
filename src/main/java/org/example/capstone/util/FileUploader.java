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

  @Value("${file.upload-dir:app/tmp/uploads}")
  private String uploadDir;

  @Value("${server.base-url:https://port-0-backend-springboot-mbhk52lab25c23a5.sel4.cloudtype.app}")
  private String baseUrl;

  public String upload(MultipartFile file) throws IOException {
    File directory = new File(uploadDir);
    if (!directory.isAbsolute()) {
      directory = new File(System.getProperty("user.dir"), uploadDir);
    }

    if (!directory.exists()) {
      if (!directory.mkdirs()) {
        throw new IOException("업로드 디렉토리 생성 실패: " + directory.getAbsolutePath());
      }
    }

    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
    File destination = new File(directory, fileName);
    file.transferTo(destination);

    return baseUrl + "/images/" + fileName;
  }


  public void delete(String imageUrl) throws IOException {
    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    File file = new File(uploadDir, fileName);

    if (file.exists()) {
      if (!file.delete()) {
        throw new IOException("파일 삭제 실패 : " + file.getAbsolutePath());
      }
    }
  }
}
