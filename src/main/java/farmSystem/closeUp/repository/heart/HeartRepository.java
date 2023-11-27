package farmSystem.closeUp.repository.heart;

import farmSystem.closeUp.domain.GuestBook;
import farmSystem.closeUp.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Heart findByGuestBook (GuestBook guestBook);
}


