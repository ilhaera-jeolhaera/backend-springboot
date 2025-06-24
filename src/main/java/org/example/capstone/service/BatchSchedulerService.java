package org.example.capstone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchSchedulerService {

  private final JobLauncher jobLauncher;
  private final Job policyJob;

  @Scheduled(cron = "0 0 0 * * ?")
  public void runPolicyJob() throws Exception {
    JobParameters params = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();

    jobLauncher.run(policyJob, params);
  }
}
