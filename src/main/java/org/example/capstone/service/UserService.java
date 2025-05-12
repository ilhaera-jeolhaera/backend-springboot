package org.example.capstone.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone.domain.User;
import org.example.capstone.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public String GetUsernameById(Long userId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다 : " + userId));
    return user.getUsername();
  }
}
