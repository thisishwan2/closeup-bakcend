package farmSystem.closeUp.dto.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetSearchCreatorResponse {

    private Long creatorId;
    private String creatorName;
    private String creatorProfileImageUrl;
    private String creatorIntroduction;

    public static GetSearchCreatorResponse of(Long creatorId, String creatorName, String creatorProfileImageUrl, String creatorIntroduction) {
        return GetSearchCreatorResponse.builder()
                .creatorId(creatorId)
                .creatorName(creatorName)
                .creatorProfileImageUrl(creatorProfileImageUrl)
                .creatorIntroduction(creatorIntroduction)
                .build();
    }
}
