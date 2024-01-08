package farmSystem.closeUp.dto.raffleProduct.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostCreateRaffleProductResponse {
    private LocalDateTime winnerDate;

    public static PostCreateRaffleProductResponse of(LocalDateTime winnerDate) {
        return PostCreateRaffleProductResponse.builder()
                .winnerDate(winnerDate)
                .build();
    }
}
