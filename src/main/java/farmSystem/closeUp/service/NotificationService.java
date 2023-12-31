package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.domain.Notification;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.dto.notification.request.PostNotificationRequest;
import farmSystem.closeUp.dto.notification.response.GetNotificationsResponse;
import farmSystem.closeUp.dto.notification.response.PostNotificationResponse;
import farmSystem.closeUp.repository.notification.NotificationRepository;
import farmSystem.closeUp.repository.notification.NotificationRepositoryImpl;
import farmSystem.closeUp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationRepositoryImpl notificationRepositoryImpl;
    private final UserService userService;
    private final UserRepository userRepository;

    // 크리에이터 공지사항 조회(무한 스크롤) - 유저
    @Transactional
    public Slice<GetNotificationsResponse> getNotifications(Long creatorId, Pageable pageable){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Slice<GetNotificationsResponse> findNotifications = notificationRepositoryImpl.findByNotifications(creatorId, pageable);

        return findNotifications;
    }

    // 크리에이터 공지사항 조회(무한 스크롤) - 크리에이터
    @Transactional
    public Slice<GetNotificationsResponse> getNotificationsCreator(Long creatorId, Pageable pageable){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Slice<GetNotificationsResponse> findNotifications = notificationRepositoryImpl.findByNotifications(creatorId, pageable);

        return findNotifications;
    }

    // 크리에이터 공지사항 작성 - 크리에이터
    @Transactional
    public PostNotificationResponse postNotification(Long creatorId, PostNotificationRequest request) {
        User user = userService.getCurrentUser();

        Notification notification = Notification.builder()
                .notificationTitle(request.getTitle())
                .notificationContent(request.getContent())
                .build();

        User creator = userRepository.findById(creatorId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));
        notification.setCreator(creator);

        notificationRepository.save(notification);

        return PostNotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .title(notification.getNotificationTitle())
                .content(notification.getNotificationContent())
                .build();

    }
}