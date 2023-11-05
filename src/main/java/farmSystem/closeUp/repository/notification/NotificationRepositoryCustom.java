package farmSystem.closeUp.repository.notification;

import farmSystem.closeUp.dto.notification.response.GetNotificationsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NotificationRepositoryCustom {

    Slice<GetNotificationsResponse> findByNotifications(Long creatorId, Pageable pageable);

}