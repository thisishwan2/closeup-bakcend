package farmSystem.closeUp.repository.pointHistory;

import farmSystem.closeUp.dto.pointHistory.response.GetPointHistoryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PointHistoryRepositoryCustom {

    Slice<GetPointHistoryResponse> findPointHistory(Long userId, Pageable pageable);
}
