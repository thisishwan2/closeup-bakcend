package farmSystem.closeUp.dto.notification.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetNotificationsResponse {

    private Long notificationId;
    private String notificationTitle;
    private String notificationContent;
    private LocalDateTime notificationCreatedAt;
    private LocalDateTime notificationModifiedAt;
    private String notificationThumbnailUrl;

    public static GetNotificationsResponse of(Long notificationId, String notificationTitle, String notificationContent, LocalDateTime notificationCreatedAt, LocalDateTime notificationModifiedAt, String notificationThumbnailUrl) {
        return GetNotificationsResponse.builder()
                .notificationId(notificationId)
                .notificationTitle(notificationTitle)
                .notificationContent(notificationContent)
                .notificationCreatedAt(notificationCreatedAt)
                .notificationModifiedAt(notificationModifiedAt)
                .notificationThumbnailUrl(notificationThumbnailUrl)
                .build();
    }
}
