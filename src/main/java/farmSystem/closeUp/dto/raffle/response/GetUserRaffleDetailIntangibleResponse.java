package farmSystem.closeUp.dto.raffle.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class GetUserRaffleDetailIntangibleResponse extends GetUserRaffleDetailResponse{
    LocalDateTime raffleWinningDate;
}
