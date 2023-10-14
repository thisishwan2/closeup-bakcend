package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.config.jwt.JwtService;
import farmSystem.closeUp.config.redis.RedisUtils;
import farmSystem.closeUp.config.security.SecurityUtils;
import farmSystem.closeUp.dto.response.TokenResponse;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisUtils redisUtils;
    private final JwtService jwtService;


    public TokenResponse reIssueToken(String refreshToken) throws Exception {
        log.info("reissue start");

        // Bearer 뺀 토큰 자체
        refreshToken = jwtService.extractToken(refreshToken);
        // 클라이언트가 보낸 토큰의 클레임으로 있는 userId를 가져옴.
        Optional<Long> userId = jwtService.getUserId(refreshToken);

        if (!userId.isPresent()){
            throw new CustomException(Result.INVALID_ACCESS);
        }

        Long getUserId = userId.get();

//        // 현재 컨텍스트 내에 있는 user
//        User currentUser = getCurrentUser();
//        Long userId = currentUser.getUserId();

        // userId로 조회한 redis 상의 토큰 value
        String findRefreshToken = redisUtils.getData(String.valueOf(getUserId));
        log.info(findRefreshToken);

//        if (findRefreshToken=="false"){
//            throw new CustomException(Result.INVALID_ACCESS);
//        }

        // 현재 사용자와 토큰에 적힌 사용자가 다르면 올바르지 않은 접근
        if (!findRefreshToken.equals(refreshToken)){
            redisUtils.deleteData(String.valueOf(getUserId));
            //로그아웃
            throw new CustomException(Result.NOTFOUND_USER);
        }

        String newRefreshToken = jwtService.createRefreshToken(getUserId);
        String newAccessToken = jwtService.createAccessToken(getUserId);
        return TokenResponse.toDto(newAccessToken, newRefreshToken);
    }


    // 아래 두 메서드는 토큰이 살아있는 동안 사용
    public Long getCurrentUserId() throws AuthenticationException {
        return SecurityUtils.getCurrentUserId();
    }

    public User getCurrentUser() throws Exception {
        return userRepository
                .findById(SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> new Exception("회원을 찾지 못함"));
    }

}
