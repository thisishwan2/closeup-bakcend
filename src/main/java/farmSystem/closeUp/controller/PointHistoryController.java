package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.pointHistory.response.GetPointHistoryResponse;
import farmSystem.closeUp.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointHistoryController {

    private final PointHistoryService pointHistoryService;

    // 포인트 충전 소비 내역 조회
    @GetMapping("/user/point-history")
    public CommonResponse<Slice<GetPointHistoryResponse>> getPointHistoryResponse(Pageable pageable){
        return CommonResponse.success(pointHistoryService.getPointHistoryResponse(pageable));
    }

}
