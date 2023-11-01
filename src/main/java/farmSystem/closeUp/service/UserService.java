package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.config.jwt.JwtService;
import farmSystem.closeUp.config.redis.RedisUtils;
import farmSystem.closeUp.config.security.SecurityUtils;
import farmSystem.closeUp.dto.response.TokenResponse;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.dto.request.UserRequestTest;
import farmSystem.closeUp.dto.response.UserResponseTest;
import farmSystem.closeUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisUtils redisUtils;
    private final JwtService jwtService;


    @Transactional
    public TokenResponse reIssueToken(String refreshToken) {
        log.info("reissue start");

        // Bearer 뺀 토큰 자체
        refreshToken = jwtService.extractToken(refreshToken);
        // 클라이언트가 보낸 토큰의 클레임으로 있는 userId를 가져옴.
        Long userId = jwtService.getUserId(refreshToken);

        // userId로 조회한 redis 상의 토큰 value
        String findRefreshToken = redisUtils.getData(String.valueOf(userId));
        log.info(findRefreshToken);

        // 현재 사용자와 토큰에 적힌 사용자가 다르면 올바르지 않은 접근
        if (!findRefreshToken.equals(refreshToken)){
            redisUtils.deleteData(String.valueOf(userId));
            //로그아웃
            throw new CustomException(Result.INVALID_TOKEN);
        }

        String newRefreshToken = jwtService.createRefreshToken(userId);
        String newAccessToken = jwtService.createAccessToken(userId);
        return TokenResponse.toDto(newAccessToken, newRefreshToken);
    }


    // 아래 두 메서드는 토큰이 살아있는 동안 사용
    public Long getCurrentUserId() throws AuthenticationException {
        return SecurityUtils.getCurrentUserId();
    }


    // 현재 사용자 조회
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        try {
            return userRepository
                    .findById(SecurityUtils.getCurrentUserId())
                    .orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));
        } catch (Exception e) {
            throw new CustomException(Result.NOTFOUND_USER);
        }
    }

    // 추가 회원가입
    @Transactional
    public UserResponseTest signUp(UserRequestTest userRequestTest){
        User currentUser = getCurrentUser();
        currentUser.signUp(userRequestTest.getNickname(), userRequestTest.getAddress(), userRequestTest.getPhoneNumber(), userRequestTest.getProfileImageUrl(), userRequestTest.getGender(), userRequestTest.getBirthday());
        currentUser.authorizeUser();
        return UserResponseTest.builder().userId(currentUser.getUserId()).build();
    }

    //권한 확인용
    @Transactional(readOnly = true)
    public User getUser(Long userId){
        Optional<User> byId = userRepository.findById(userId);
        User user = byId.get();
        return user;
    }

}
