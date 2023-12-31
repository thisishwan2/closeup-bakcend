package farmSystem.closeUp.dto.guestbook.response;

import farmSystem.closeUp.dto.raffleProduct.response.PostRaffleProductResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostGuestBooksResponse {
    private Long guestBookId;
    private String content;

    public static PostGuestBooksResponse of(Long guestBookId, String content) {
        return PostGuestBooksResponse.builder()
                .guestBookId(guestBookId)
                .content(content)
                .build();

    }

}