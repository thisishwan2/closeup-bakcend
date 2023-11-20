package farmSystem.closeUp.dto.raffle.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GetUserRaffleDetailTangible extends GetUserRaffleDetailResponse {
    String raffleUserAddress;
}
