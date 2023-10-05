package farmSystem.closeUp.service;

import farmSystem.closeUp.config.SecurityUtils;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;


    public Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }

    public User getCurrentUser() {
        return userRepository
                .findById(SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 못찾음"));
    }
}
