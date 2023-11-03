package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.platform.response.GetPlatformsResponse;
import farmSystem.closeUp.dto.response.TokenResponse;
import farmSystem.closeUp.service.PlatformService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PlatformController {

    private final PlatformService platformService;

    // 플랫폼 전체 조회
    @GetMapping("/user/platforms")
    public CommonResponse<List<GetPlatformsResponse>> getPlatforms() {
        return CommonResponse.success(platformService.getPlatforms());
    }

}
