package farmSystem.closeUp.repository;


import farmSystem.closeUp.domain.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
}
