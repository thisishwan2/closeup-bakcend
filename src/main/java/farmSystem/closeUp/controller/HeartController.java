package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.heart.response.PostHeartResponse;
import farmSystem.closeUp.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    // 크리에이터 - 방명록 반응 남기기 (좋아요)
    @PostMapping("/creator/guestbooks/{guestbookId}/heart")
    public CommonResponse<PostHeartResponse> postHeart(@PathVariable("guestbookId") Long guestbookId) {
        return CommonResponse.success(heartService.postHeart(guestbookId));
    }
}
