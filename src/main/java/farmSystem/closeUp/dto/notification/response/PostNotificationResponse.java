package farmSystem.closeUp.dto.notification.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostNotificationResponse {
    private Long notificationId;
    private String title;
    private String content;

    public static PostNotificationResponse of(Long notificationId, String title,  String content) {
        return PostNotificationResponse.builder()
                .notificationId(notificationId)
                .title(title)
                .content(content)
                .build();
    }
}