package farmSystem.closeUp.config.jwt;

import farmSystem.closeUp.config.redis.RedisUtils;
import farmSystem.closeUp.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    @Value("${jwt.token.secret-key}")
    private String secretKey;
    @Value("${jwt.access-token.expiration}")
    private Long accessTokenExpirationPeriod;
    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpirationPeriod;
    @Value("${jwt.access-token.header}")
    private String accessHeader;
    @Value("${jwt.refresh-token.header}")
    private String refreshHeader;

    private final UserRepository userRepository;
    private final RedisUtils redisUtils;
    private Key key;

    /**
     * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "email"으로 설정
     * JWT의 헤더에 들어오는 값 : 'Authorization(Key) = Bearer {토큰} (Value)' 형식
     */
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";


    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(Long userId, String email) {
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setSubject(userId.toString())
                .claim(EMAIL_CLAIM, email)
                .setExpiration(new Date(now.getTime() + accessTokenExpirationPeriod))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken(Long userId) {
        Date now = new Date();
        String refreshToken = Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(new Date(now.getTime() + accessTokenExpirationPeriod))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        redisUtils.setData(userId.toString(), refreshToken, refreshTokenExpirationPeriod); // redis에 userId:refreshToken 형태로 저장
        return refreshToken;
    }

    /**
     * AccessToken 재발급시 헤더에 실어서 보내기
     */
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    /**
     * AccessToken + RefreshToken 헤더에 실어서 보내기
     */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken){
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    /**
     * RefreshToken (업데이트)
     */
    public void updateRefreshToken(Long userId, String refreshToken) {
        redisUtils.setData(String.valueOf(userId), refreshToken, refreshTokenExpirationPeriod);
    }

    /**
     * 클라이언트 요청 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * 클라이언트 요청 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    // 토큰으로 회원 아이디 정보 조회
    public Long getUserId(String token) {
        return Long.valueOf(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
    }

    // 토큰으로 회원 이메일 정보 조회
    public String getUserEmail(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(EMAIL_CLAIM);
    }

    //유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // Token 남은 유효시간
    public Long getExpiration(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        // 현재 시간
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    // access Token 재발급(재발급은 accessToken이 만료 되기 직전에 실행됨)
    /*
    public String reissue(UserRequest.reissue request){

        // 1. refresh token 검증
        validateToken(request.getRefreshToken());

        // 2. access token에서 userId(setSubject) 가져오기
        Authentication authentication = getAuthentication(request.getAccessToken());

        // 3. redis에서 userId 기반 저장된 refresh token 값을 가져옴.
        String refreshToken = redisUtil.getData(authentication.getName());
        if(!refreshToken.equals(request.getRefreshToken())){
            throw new CustomException(Result.INVALID_REFRESH_TOKEN);
        }

        // 로그아웃시 redis에 refresh token이 존재하지 않는 경우 처리
        if(ObjectUtils.isEmpty(refreshToken)){
            throw new CustomException(Result.BAD_REQUEST);
        }
        if(!refreshToken.equals(request.getRefreshToken())) {
            throw new CustomException(Result.INVALID_REFRESH_TOKEN);
        }

        // 4. 새로운 access token 생성
        String accessToken = createToken(Long.valueOf(authentication.getName()), authentication);

        // 5. refresh token redis 업데이트
        redisUtil.setDataExpire(authentication.getName(), accessToken, this.refreshTokenValidTime);

        return accessToken;
    }
    */
}