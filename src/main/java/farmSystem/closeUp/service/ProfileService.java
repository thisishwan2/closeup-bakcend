package farmSystem.closeUp.service;

import farmSystem.closeUp.dto.profile.response.GetProfileResponse;
import farmSystem.closeUp.repository.profile.ProfileRepository;
import farmSystem.closeUp.repository.profile.ProfileRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileRepositoryImpl profileRepositoryImpl;

    // 크리에이터 홈 - 프로필 조회
    @Transactional
    public GetProfileResponse getProfile (Long creatorId) {
        GetProfileResponse findProfile = profileRepositoryImpl.findByProfile(creatorId);

        return findProfile;
    }

}
