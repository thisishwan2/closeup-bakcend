package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.domain.Raffle;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.domain.WinningInfo;
import farmSystem.closeUp.dto.raffle.response.GetRafflesResponse;
import farmSystem.closeUp.dto.raffle.response.GetRafflesUserResponse;
import farmSystem.closeUp.repository.raffle.RaffleRepository;
import farmSystem.closeUp.repository.raffle.RaffleRepositoryImpl;
import farmSystem.closeUp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaffleService {
    private final RaffleRepository raffleRepository;
    private final RaffleRepositoryImpl raffleRepositoryImpl;
    private final UserService userService;
    private final UserRepository userRepository;

    // 크리에이터 래플 조회 (무한 스크롤)
    @Transactional
    public Slice<GetRafflesResponse> getRaffles(Long creatorId, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Slice<GetRafflesResponse> findRaffles = raffleRepositoryImpl.findByRaffles(creatorId, pageable);

        return findRaffles;
    }

    // 유저의 당첨된 래플 조회
    public List<GetRafflesUserResponse> getRafflesUserWinning() {
        Long userId = null;
        try {
            userId = userService.getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));

        List<Raffle> raffles = raffleRepository.findAllByUserAndWinningInfo(user, WinningInfo.WINNING);

        List<GetRafflesUserResponse> result = new ArrayList<>();
        for (Raffle raffle : raffles) {
            GetRafflesUserResponse getRafflesResponse = GetRafflesUserResponse.builder()
                    .raffleId(raffle.getRaffleId())
                    .winningInfo(raffle.getWinningInfo())
                    .raffleCreatedAt(raffle.getRaffleProduct().getCreatedAt())
                    .raffleEndAt(raffle.getRaffleProduct().getEndDate())
                    .raffleThumbnailUrl(raffle.getRaffleProduct().getThumbnailImageUrl())
                    .creatorId(raffle.getRaffleProduct().getCreator().getUserId())
                    .creatorName(raffle.getRaffleProduct().getCreator().getNickName())
                    .build();
            result.add(getRafflesResponse);
        }

        return result;
    }

    public List<GetRafflesUserResponse> getRafflesUserAll() {
        Long userId = null;
        try {
            userId = userService.getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));

        List<Raffle> raffles = raffleRepository.findAllByUser(user);

        List<GetRafflesUserResponse> result = new ArrayList<>();
        for (Raffle raffle : raffles) {
            GetRafflesUserResponse getRafflesResponse = GetRafflesUserResponse.builder()
                    .raffleId(raffle.getRaffleId())
                    .winningInfo(raffle.getWinningInfo())
                    .raffleCreatedAt(raffle.getRaffleProduct().getCreatedAt())
                    .raffleEndAt(raffle.getRaffleProduct().getEndDate())
                    .raffleThumbnailUrl(raffle.getRaffleProduct().getThumbnailImageUrl())
                    .creatorId(raffle.getRaffleProduct().getCreator().getUserId())
                    .creatorName(raffle.getRaffleProduct().getCreator().getNickName())
                    .build();
            result.add(getRafflesResponse);
        }

        return result;
    }
}
