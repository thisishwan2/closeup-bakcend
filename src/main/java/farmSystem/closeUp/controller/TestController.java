package farmSystem.closeUp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/main")
    public String main(){
        return "ok";
    }

    @GetMapping("/jwt-test")
    public String jwtTest(){
        log.info("success test");
        return "jwt";
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

    @GetMapping("/login-success")
    public String loginSuccess(HttpServletRequest request){
        // URL 매개변수에서 access_token 및 refresh_token 값을 가져옴
        String accessToken = request.getParameter("access_token");
        String refreshToken = request.getParameter("refresh_token");
        log.info(accessToken);
        log.info(refreshToken);
        return "로그인 성공 -> 메인화면";
    }
}
