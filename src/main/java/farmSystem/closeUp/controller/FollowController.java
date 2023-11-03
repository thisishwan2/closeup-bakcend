package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.follow.response.GetFollowingResponse;
import farmSystem.closeUp.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 회원이 팔로우한 크리에이터 조회
    @GetMapping("/user/following")
    public CommonResponse<List<GetFollowingResponse>> getFollowingList() {
        return CommonResponse.success(followService.getFollowingList());
    }

}
