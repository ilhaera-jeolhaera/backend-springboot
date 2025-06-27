package org.example.capstone.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUploader {

  @Value("${cloudflare.r2.access-key}")
  private String accessKey;

  @Value("${cloudflare.r2.secret-key}")
  private String secretKey;

  @Value("${cloudflare.r2.endpoint}")
  private String endpoint;

  @Value("${cloudflare.r2.public-url}")
  private String publicUrl;

  @Value("${cloudflare.r2.bucket}")
  private String bucketName;

  @Value("${cloudflare.r2.region}")
  private String region;

  private S3Client s3Client;

  @PostConstruct
  public void init() {
    s3Client = S3Client.builder()
            .endpointOverride(URI.create(endpoint))
            .credentialsProvider(
                    StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey, secretKey)
                    )
            )
            .region(Region.of(region))
            .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
            .build();
  }

  public String upload(MultipartFile file) throws IOException {
    String fileName = "post/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

    PutObjectRequest request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .contentType(file.getContentType())
            .build();

    s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

    return publicUrl + "/" + fileName;
  }

  public void delete(String imageUrl) {
    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

    DeleteObjectRequest request = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .build();

    s3Client.deleteObject(request);
  }
}
