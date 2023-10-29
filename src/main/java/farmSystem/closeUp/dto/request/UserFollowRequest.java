package farmSystem.closeUp.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserFollowRequest {
    Long userId;
    Long[] creatorId;
}
