package farmSystem.closeUp.dto.guestbook.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetGuestBooksResponse {
    private Long guestBookId;
    private Long guestBookUserId;
    private String guestBookUserNickname;
    private String guestBookUserProfileImageUrl;
    private LocalDateTime guestBookCreatedAt;
    private String guestBookContent;
    private boolean guestBookIsLiked;

    public static GetGuestBooksResponse of(Long guestBookId, Long guestBookUserId, String guestBookUserNickname, String guestBookUserProfileImageUrl, LocalDateTime guestBookCreatedAt, String guestBookContent, boolean guestBookIsLiked) {
        return GetGuestBooksResponse.builder()
                .guestBookId(guestBookId)
                .guestBookUserId(guestBookUserId)
                .guestBookUserNickname(guestBookUserNickname)
                .guestBookUserProfileImageUrl(guestBookUserProfileImageUrl)
                .guestBookCreatedAt(guestBookCreatedAt)
                .guestBookContent(guestBookContent)
                .guestBookIsLiked(guestBookIsLiked)
                .build();
    }
}
