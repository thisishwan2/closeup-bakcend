package farmSystem.closeUp.dto.follow.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetFollowingResponse {

    private Long creatorId;
    private String creatorName;
    private String creatorProfileImageUrl;

    public static GetFollowingResponse of(Long creatorId, String creatorName, String creatorProfileImageUrl) {
        return GetFollowingResponse.builder()
                .creatorId(creatorId)
                .creatorName(creatorName)
                .creatorProfileImageUrl(creatorProfileImageUrl)
                .build();
    }
}
