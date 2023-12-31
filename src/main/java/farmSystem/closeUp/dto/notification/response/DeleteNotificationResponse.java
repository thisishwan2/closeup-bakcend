package farmSystem.closeUp.dto.notification.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteNotificationResponse {
    private Long notificationId;

    public static PostNotificationResponse of(Long notificationId) {
        return PostNotificationResponse.builder()
                .notificationId(notificationId)
                .build();
    }
}