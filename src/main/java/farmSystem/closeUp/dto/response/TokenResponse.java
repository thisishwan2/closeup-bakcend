package farmSystem.closeUp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponse {

    private final String accessToken;
    private final String refreshToken;

    public static TokenResponse toDto(String accessToken, String refreshToken){
        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
}
