package farmSystem.closeUp.service;

import farmSystem.closeUp.dto.notification.response.GetNotificationsResponse;
import farmSystem.closeUp.repository.notification.NotificationRepository;
import farmSystem.closeUp.repository.notification.NotificationRepositoryImpl;
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


    // 크리에이터 공지사항 조회(무한 스크롤)
    @Transactional
    public Slice<GetNotificationsResponse> getNotifications(Long creatorId, Pageable pageable){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Slice<GetNotificationsResponse> findNotifications = notificationRepositoryImpl.findByNotifications(creatorId, pageable);

        return findNotifications;
    }
}
