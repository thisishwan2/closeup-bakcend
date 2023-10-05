package farmSystem.closeUp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckingController {

    @GetMapping("/health-check")
    public String HealthChecking (){
        return "ok";
    }
}
