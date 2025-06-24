package org.example.capstone.batch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.example.capstone.entity.Policy;
import org.example.capstone.repository.PolicyRepository;

@Component
@RequiredArgsConstructor
public class PolicyItemWriter implements ItemWriter<Policy> {

  private final PolicyRepository policyRepository;

  @Override
  public void write(Chunk<? extends Policy> chunk) throws Exception {
    List<? extends Policy> items = chunk.getItems();
    policyRepository.saveAll(items);
  }
}
