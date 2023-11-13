package farmSystem.closeUp.repository.follow;

import farmSystem.closeUp.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByUserUserId(Long userId);
}