package farmSystem.closeUp.repository.notification;

import farmSystem.closeUp.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {

}