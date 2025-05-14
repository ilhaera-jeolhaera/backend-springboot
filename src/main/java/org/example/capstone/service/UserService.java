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
    User user = userRepository.findUserById(userId);
    if (user == null) {
      throw new RuntimeException("유저를 찾을 수 없습니다 : " + userId);
    }
    return user.getUsername();
  }
}
