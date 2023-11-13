package farmSystem.closeUp.dto.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostTokenReissueResponse {

    private final String accessToken;
    private final String refreshToken;

    public static PostTokenReissueResponse of(String accessToken, String refreshToken){
        return PostTokenReissueResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
}
