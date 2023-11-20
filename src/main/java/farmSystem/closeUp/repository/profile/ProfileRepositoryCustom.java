package farmSystem.closeUp.repository.profile;

import farmSystem.closeUp.dto.profile.response.GetProfileResponse;

public interface ProfileRepositoryCustom {
    GetProfileResponse findByProfile(Long creatorId);
}
