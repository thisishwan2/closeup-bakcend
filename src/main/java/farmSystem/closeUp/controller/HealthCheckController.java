package farmSystem.closeUp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        return ("health check - 카카오 로그인 잘되는지 확인");
    }
}
