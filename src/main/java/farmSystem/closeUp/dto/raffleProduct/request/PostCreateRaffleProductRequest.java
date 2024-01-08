package farmSystem.closeUp.dto.raffleProduct.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostCreateRaffleProductRequest {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String content;
    private Long winnerCount;
    private Long rafflePrice;
    private Long categoryId;
    private PostCreateRaffleProductExtraRequest extraRequest;
}
