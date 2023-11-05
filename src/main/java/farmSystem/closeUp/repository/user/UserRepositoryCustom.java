package farmSystem.closeUp.repository.user;

import farmSystem.closeUp.dto.user.response.GetSearchCreatorResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface UserRepositoryCustom {

    Slice<GetSearchCreatorResponse> findByPlatform(Long platformId, Pageable pageable);
}
