package farmSystem.closeUp.service;

import farmSystem.closeUp.domain.Platform;
import farmSystem.closeUp.dto.platform.response.GetPlatformsResponse;
import farmSystem.closeUp.repository.PlatformRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlatformService {

    private final PlatformRepository platformRepository;

    public List<GetPlatformsResponse> getPlatforms() {

        List<GetPlatformsResponse> getPlatformsResponses = new ArrayList<>();

        List<Platform> platforms = platformRepository.findAll();
        for (Platform platform : platforms) {
            getPlatformsResponses.add(GetPlatformsResponse.of(platform.getPlatformId(), platform.getPlatformName(), platform.getPlatformImageUrl()));
        }
        return getPlatformsResponses;
    }
}
