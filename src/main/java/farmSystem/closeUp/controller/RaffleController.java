package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.raffle.response.GetRafflesResponse;
import farmSystem.closeUp.dto.raffle.response.GetRafflesUserResponse;
import farmSystem.closeUp.dto.raffle.response.GetUserRaffleDetailResponse;
import farmSystem.closeUp.service.RaffleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RaffleController {

    private final RaffleService raffleService;

    @GetMapping("/user/raffles")
    public CommonResponse<List<GetRafflesUserResponse>> getRafflesAll() {
        return CommonResponse.success(raffleService.getRafflesUserAll());
    }

    @GetMapping("/user/raffles/winning")
    public CommonResponse<List<GetRafflesUserResponse>> getRafflesWinning() {
        return CommonResponse.success(raffleService.getRafflesUserWinning());
    }

    @GetMapping("/user/raffles/{raffleId}")
    public CommonResponse<GetUserRaffleDetailResponse> getRaffleDetail(@PathVariable("raffleId") Long raffleId) {
        return CommonResponse.success(raffleService.getRaffleDetail(raffleId));
    }
}
