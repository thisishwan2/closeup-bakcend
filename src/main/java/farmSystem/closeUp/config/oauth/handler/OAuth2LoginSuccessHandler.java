package farmSystem.closeUp.config.oauth.handler;

import farmSystem.closeUp.config.oauth.CustomOAuth2User;
import farmSystem.closeUp.config.jwt.JwtService;
import farmSystem.closeUp.domain.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);

            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if(oAuth2User.getUserRole() == UserRole.GUEST) {
                String accessToken = jwtService.createAccessToken(oAuth2User.getUserId());
                String refreshToken = jwtService.createRefreshToken(oAuth2User.getUserId());
                log.info(accessToken);
                log.info(refreshToken);

                String redirectURL = "http://localhost:8080/login?access_token=" + accessToken + "&refresh_token=" + refreshToken;
//                String redirectURL = "http://localhost:8080/login?access_token=" + accessToken + "&refresh_token=" + refreshToken; 프론트 URL로 변경
                response.sendRedirect(redirectURL);
                log.info("추가 회원가입 진행");
            } else {
                loginSuccess(response, oAuth2User); // 처음 로그인이 아니고 회원인 경우 로그인에 성공한 경우 access, refresh 토큰 생성
                log.info("로그인 성공 메인페이지 이동");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getUserId());
        String refreshToken = jwtService.createRefreshToken(oAuth2User.getUserId()); //여기서 refreshToken 업데이트 됨
//        log.info(accessToken);
//        log.info(refreshToken);

        //여기서 일반 회원인지 크리에이터인지에 따라 메인 페이지 리다이렉트를 다르게 해줘야한다.(현재는 그냥 회원의 페이지로 생각함.)
        String redirectURL = "http://localhost:8080/login-success?access_token=" + accessToken + "&refresh_token=" + refreshToken;
//        String redirectURL = "http://localhost:8080/login?access_token=" + accessToken + "&refresh_token=" + refreshToken; 프론트 URL로 변경
        response.sendRedirect(redirectURL);

    }
}
