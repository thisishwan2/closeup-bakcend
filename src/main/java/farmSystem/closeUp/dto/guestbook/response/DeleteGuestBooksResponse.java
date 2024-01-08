package farmSystem.closeUp.dto.guestbook.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteGuestBooksResponse {
    private Long guestBookId;

    public static PostGuestBooksResponse of(Long guestBookId) {
        return PostGuestBooksResponse.builder()
                .guestBookId(guestBookId)
                .build();

    }
}
