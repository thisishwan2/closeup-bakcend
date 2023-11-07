package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.domain.Follow;
import farmSystem.closeUp.domain.RaffleProduct;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.dto.raffleProduct.response.GetRaffleProductResponse;
import farmSystem.closeUp.dto.raffleProduct.response.GetRaffleProductsResponse;
import farmSystem.closeUp.repository.follow.FollowRepository;
import farmSystem.closeUp.repository.raffleProduct.RaffleProductRepository;
import farmSystem.closeUp.repository.raffleProduct.RaffleProductRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaffleProductService {

    private final RaffleProductRepository raffleProductRepository;
    private final RaffleProductRepositoryImpl raffleProductRepositoryImpl;
    private final FollowRepository followRepository;
    private final UserService userService;

    // 회원님이 팔로우하는 크리에이터 래플 목록 조회
    @Transactional(readOnly = true)
    public Slice<GetRaffleProductsResponse> getFollowingRaffleProducts(Pageable pageable) throws AuthenticationException {
        Long userId = userService.getCurrentUserId();
        List<Follow> followList = followRepository.findByUserUserId(userId);

        List<Long> creatorIds = followList
                .stream()
                .map(follow -> follow.getCreator().getUserId())
                .collect(Collectors.toList());

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Slice<GetRaffleProductsResponse> followingRaffleProducts = raffleProductRepositoryImpl.findFollowingRaffleProducts(creatorIds, pageable);

        return followingRaffleProducts;
    }

    // 전체 래플 목록 조회
    @Transactional(readOnly = true)
    public Slice<GetRaffleProductsResponse> getRaffleProducts(Pageable pageable){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Slice<GetRaffleProductsResponse> raffleProducts = raffleProductRepositoryImpl.findRaffleProducts(pageable);
        return raffleProducts;
    }

    // 래플 상세 조회
    @Transactional(readOnly = true)
    public GetRaffleProductResponse getRaffleProduct(Long raffleProductId){
        RaffleProduct raffleProduct = raffleProductRepository.findById(raffleProductId).orElseThrow(() -> new CustomException(Result.NOTFOUND_RAFFLE));
        GetRaffleProductResponse getRaffleProductResponse = GetRaffleProductResponse.
                of(raffleProduct.getRaffleProductId(), raffleProduct.getTitle(), raffleProduct.getStartDate(), raffleProduct.getEndDate(), raffleProduct.getContent(), raffleProduct.getWinnerCount(), raffleProduct.getRafflePrice(), raffleProduct.getWinningDate(), raffleProduct.getThumbnailImageUrl(), raffleProduct.getCreator().getNickName(), raffleProduct.getCreator().getUserId());
        return getRaffleProductResponse;
    }
}
