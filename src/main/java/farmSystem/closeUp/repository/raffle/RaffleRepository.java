package farmSystem.closeUp.repository.raffle;

import farmSystem.closeUp.domain.Raffle;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.domain.WinningInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RaffleRepository extends JpaRepository<Raffle, Long> {
    // 특정 유저의 래플 응모 목록 조회
    List<Raffle> findAllByUserAndWinningInfo(User user, WinningInfo winningInfo);

    List<Raffle> findAllByUser(User user);

    Optional<Raffle> findByRaffleId(Long raffleId);
}
