package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.dto.request.UserRequestTest;
import farmSystem.closeUp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;

    @GetMapping("/main")
    public String main(){
        return "ok";
    }

    @GetMapping("/jwt-test")
    public User jwtTest() throws Exception {
        log.info("success test");
        User currentUser = userService.getCurrentUser();
        return currentUser;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        // URL 매개변수에서 access_token 및 refresh_token 값을 가져옴
        String accessToken = request.getParameter("access_token");
        String refreshToken = request.getParameter("refresh_token");
        log.info(accessToken);
        log.info(refreshToken);
        return "추가 회원가입 화면";
    }

    @PostMapping("/sign-up")
    public CommonResponse signUp(@RequestBody UserRequestTest userRequestTest) throws Exception {
        return CommonResponse.success(userService.signUp(userRequestTest));
    }

    @GetMapping("/user/{userId}")
    public CommonResponse getUserInfo(@PathVariable Long userId) throws Exception {
        return CommonResponse.success(userService.getUser(userId));
    }



    // oauthsuccesshandler에서 리다이렉트 시킨 url로 찍히는지 테스트
    @GetMapping("/login-success-test")
    public String loginSuccess(HttpServletRequest request){
        // URL 매개변수에서 access_token 및 refresh_token 값을 가져옴
        String accessToken = request.getParameter("accessToken");
        String refreshToken = request.getParameter("refreshToken");
        log.info(accessToken);
        log.info(refreshToken);
        return "첫 회원 가입 후 로그인 성공 -> 추가 가입 페이지";
    }
}
