package farmSystem.closeUp.service;

import farmSystem.closeUp.domain.Follow;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.dto.follow.response.GetFollowingResponse;
import farmSystem.closeUp.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

import static farmSystem.closeUp.config.security.SecurityUtils.getCurrentUserId;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    // USER - 회원이 팔로우한 크리에이터 조회
    @Transactional
    public List<GetFollowingResponse> getFollowingList(){
//        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));

        Long userId = null;
        List<GetFollowingResponse> getFollowingResponses = new ArrayList<>();
        try {
            userId = getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

        List<Follow> follows = followRepository.findByUserUserId(userId);

        for (Follow follow : follows) {
            User creator = follow.getCreator();
            GetFollowingResponse getFollowingResponse = GetFollowingResponse.of(creator.getUserId(), creator.getNickName(), creator.getProfileImageUrl());
            getFollowingResponses.add(getFollowingResponse);
        }

        return getFollowingResponses;
    }
}
