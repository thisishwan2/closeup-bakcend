package farmSystem.closeUp.controller;


import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.request.UserFollowRequest;
import farmSystem.closeUp.dto.request.UserInfoRequest;
import farmSystem.closeUp.dto.request.UserInterestRequest;
import farmSystem.closeUp.dto.response.TokenResponse;
import farmSystem.closeUp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    // 헤더에 refreshToken을 보내는 경우 => AccessToken 만료 직전
    // 따라서 redis의 AccessToken과 비교후 일치한다면 accessToken, refreshToken 재발급
    @PostMapping("/token/reissue")
    public CommonResponse<TokenResponse> reIssueToken(@RequestHeader("Authorization-refresh") final String refreshToken) throws Exception {
        TokenResponse tokenResponse = userService.reIssueToken(refreshToken);
        return CommonResponse.success(tokenResponse);
    }

    @PostMapping(value = "/sign-up")
    public CommonResponse signUp(@RequestBody @Valid final UserInfoRequest userInfoRequest) throws Exception {
        Boolean signUpResult = userService.signUp(userInfoRequest);
        return CommonResponse.success();
    }

    @PostMapping(value = "/sign-up/follow")
    public CommonResponse follow(@RequestBody final UserFollowRequest userFollowRequest) throws Exception {
        Boolean signUpResult = userService.followBulk(userFollowRequest);
        return CommonResponse.success();
    }

    @PostMapping(value = "/sign-up/interest")
    public CommonResponse interest(@RequestBody final UserInterestRequest userInterestRequest) throws Exception {
        Boolean userInterestResult = userService.interestBulk(userInterestRequest);
        return CommonResponse.success();
    }
}
