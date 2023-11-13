package farmSystem.closeUp.dto.platform.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPlatformsResponse {

    private Long platformId;
    private String platformName;
    private String platformImageUrl;

    public static GetPlatformsResponse of(Long platformId, String platformName, String platformImageUrl) {
        return GetPlatformsResponse.builder()
                .platformId(platformId)
                .platformName(platformName)
                .platformImageUrl(platformImageUrl)
                .build();
    }
}
