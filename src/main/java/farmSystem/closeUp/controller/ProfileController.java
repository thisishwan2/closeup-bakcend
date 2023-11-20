package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.profile.response.GetProfileResponse;
import farmSystem.closeUp.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // 크리에이터 탭 상단 화면(안바뀜) - 크리에이터 프로필 조회
    @GetMapping("/user/{creatorId}")
    public CommonResponse<GetProfileResponse> getProfile(@PathVariable("creatorId") Long creatorId) {
        return CommonResponse.success(profileService.getProfile(creatorId));
    }
}
