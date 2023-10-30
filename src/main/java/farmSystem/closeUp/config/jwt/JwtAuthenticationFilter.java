package farmSystem.closeUp.config.jwt;

import farmSystem.closeUp.config.redis.RedisUtils;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.repository.UserRepository;
import farmSystem.closeUp.service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RedisUtils redisUtils;
    private final UserService userService;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().contains("/main") || request.getRequestURI().contains("/login-success") || request.getRequestURI().contains("/login")
                || request.getRequestURI().contains("/reissue");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("JwtAuthFilter 동작");
        String requestURI = request.getRequestURI();

        //accessToken 꺼내기
//        String accessToken = jwtService.extractAccessToken(request).get();
        String accessToken = jwtService.extractAccessToken(request)
                .filter(jwtService::validateToken)
                .orElse(null);
        log.info("accesstoken = "+ accessToken);

        // 토큰 검사 생략(permitall URL의 경우 토큰 검사 통과)
        if (!StringUtils.hasText(accessToken)) {
            log.info("토큰 검사 생략");
            doFilter(request, response, filterChain);
            return;
        }

        if(!jwtService.validateToken(accessToken)){
            log.info("유효한 JWT 토큰이 없습니다, uri {}", requestURI);
            throw new JwtException("토큰이 없답니당");
        }

        // 토큰 존재 및 유효성 검증
        if (jwtService.validateToken(accessToken)) {
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }

        filterChain.doFilter(request, response);
    }


    /**
     * [액세스 토큰 체크 & 인증 처리 메소드]
     * request에서 extractAccessToken()으로 액세스 토큰 추출 후, isTokenValid()로 유효한 토큰인지 검증
     * 유효한 토큰이면, 액세스 토큰에서 extractEmail로 Email을 추출한 후 findByEmail()로 해당 이메일을 사용하는 유저 객체 반환
     * 그 유저 객체를 saveAuthentication()으로 인증 처리하여
     * 인증 허가 처리된 객체를 SecurityContextHolder에 담기
     * 그 후 다음 인증 필터로 진행
     */
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtService.extractAccessToken(request)
                .filter(jwtService::validateToken)
                .ifPresent(accessToken -> jwtService.getUserId(accessToken)
                        .ifPresent(userId -> userRepository.findById(userId)
                                .ifPresent(this::saveAuthentication)));

    }

    /**
     * [인증 허가 메소드]
     * 파라미터의 유저 : 우리가 만든 회원 객체 / 빌더의 유저 : UserDetails의 User 객체
     *
     * new UsernamePasswordAuthenticationToken()로 인증 객체인 Authentication 객체 생성
     * UsernamePasswordAuthenticationToken의 파라미터
     * 1. 위에서 만든 UserDetailsUser 객체 (유저 정보)
     * 2. credential(보통 비밀번호로, 인증 시에는 보통 null로 제거)
     * 3. Collection < ? extends GrantedAuthority>로,
     * UserDetails의 User 객체 안에 Set<GrantedAuthority> authorities이 있어서 getter로 호출한 후에,
     * new NullAuthoritiesMapper()로 GrantedAuthoritiesMapper 객체를 생성하고 mapAuthorities()에 담기
     *
     * SecurityContextHolder.getContext()로 SecurityContext를 꺼낸 후,
     * setAuthentication()을 이용하여 위에서 만든 Authentication 객체에 대한 인증 허가 처리
     */
    public void saveAuthentication(User myUser) {
        log.info("인증객체 저장 시작");
        String password = myUser.getPassword();
        if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
            password = PasswordUtil.generateRandomPassword();
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(String.valueOf(myUser.getUserId()))
                .password(password)
                .roles(String.valueOf(myUser.getUserRole().GUEST))
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("인증객체 저장");
    }
}
