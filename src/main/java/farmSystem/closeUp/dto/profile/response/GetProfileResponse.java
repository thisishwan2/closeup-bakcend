package farmSystem.closeUp.dto.profile.response;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class GetProfileResponse {
    private Long creatorId;
    private String creatorName;
    private String creatorProfileImageUrl;
    private String creatorIntroduction;

    public static GetProfileResponse of(Long creatorId, String creatorName, String creatorProfileImageUrl, String creatorIntroduction) {
        return GetProfileResponse.builder()
                .creatorId(creatorId)
                .creatorName(creatorName)
                .creatorProfileImageUrl(creatorProfileImageUrl)
                .creatorIntroduction(creatorIntroduction)
                .build();
    }


}
