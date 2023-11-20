package farmSystem.closeUp.dto.raffle.response;

import farmSystem.closeUp.domain.WinningInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetRafflesUserResponse {
    private Long raffleId;
    private WinningInfo winningInfo;
    private String raffleTitle;
    private LocalDateTime raffleCreatedAt;
    private LocalDateTime raffleEndAt;
    private String raffleThumbnailUrl;
    private Long creatorId;
    private String creatorName;
}
