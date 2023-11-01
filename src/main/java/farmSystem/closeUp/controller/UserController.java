package farmSystem.closeUp.controller;


import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.response.TokenResponse;
import farmSystem.closeUp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    // 따라서 redis의 refreshToken과 전달받은 refreshToken 비교 후 일치한다면 accessToken, refreshToken 재발급
    @PostMapping("/token/reissue")
    public CommonResponse<TokenResponse> reIssueToken(@RequestHeader("Authorization-refresh") @Valid final String refreshToken) {
        TokenResponse tokenResponse = userService.reIssueToken(refreshToken);
        return CommonResponse.success(tokenResponse);
    }
}
