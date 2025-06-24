package org.example.capstone.batch.config;

import lombok.RequiredArgsConstructor;
import org.example.capstone.batch.processor.PolicyItemProcessor;
import org.example.capstone.batch.reader.PolicyItemReader;
import org.example.capstone.batch.writer.PolicyItemWriter;
import org.example.capstone.dto.PolicyDto;
import org.example.capstone.entity.Policy;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchJobConfig {

  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;

  private final PolicyItemReader reader;
  private final PolicyItemProcessor processor;
  private final PolicyItemWriter writer;

  @Bean
  public Step policyStep() {
    return new StepBuilder("policyStep", jobRepository)
            .<PolicyDto, Policy>chunk(50, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
  }

  @Bean
  public Job policyJob() {
    return new JobBuilder("policyJob", jobRepository)
            .start(policyStep())
            .build();
  }
}
