package farmSystem.closeUp.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    GUEST("ROLE_GUEST"), USER("ROLE_USER"), CREATOR("ROLE_CREATOR"), SIGNUP_USER("ROLE_SIGNUP"), FOLLOWED_USER("ROLE_FOLLOW"), INTERESTED_USER("ROLE_INTEREST"); // oauth 첫 로그인시에는 Guest, 이후 추가 회원가입시 User
    private final String key;
}
