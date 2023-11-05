package farmSystem.closeUp.repository.notification;

import com.querydsl.jpa.impl.JPAQueryFactory;
import farmSystem.closeUp.domain.Notification;
import farmSystem.closeUp.dto.notification.response.GetNotificationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static farmSystem.closeUp.domain.QNotification.notification;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<GetNotificationsResponse> findByNotifications(Long creatorId, Pageable pageable) {
        List<Notification> findNotifications = queryFactory
                .selectFrom(notification)
                .where(notification.creator.userId.eq(creatorId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(notification.createdAt.desc())
                .fetch();

        List<GetNotificationsResponse> result = new ArrayList<>();

        for (Notification findNotification : findNotifications) {
            result.add(GetNotificationsResponse.of(findNotification.getNotificationId(), findNotification.getNotificationTitle(), findNotification.getNotificationContent(), findNotification.getCreatedAt(), findNotification.getModifiedAt(), findNotification.getNotificationThumbnailUrl()));
        }

        return new SliceImpl<>(result, pageable, hasNextPage(result, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<GetNotificationsResponse> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
