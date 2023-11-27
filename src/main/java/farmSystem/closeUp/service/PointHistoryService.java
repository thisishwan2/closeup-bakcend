package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.dto.pointHistory.response.GetPointHistoryResponse;
import farmSystem.closeUp.repository.pointHistory.PointHistoryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class PointHistoryService {
    
    private final PointHistoryRepositoryImpl pointHistoryRepositoryImpl;
    private final UserService userService;


    public Slice<GetPointHistoryResponse> getPointHistoryResponse(Pageable pageable) {

        Long userId;
        try {
            userId = userService.getCurrentUserId();

        } catch (AuthenticationException e) {
            throw new CustomException(Result.UNAUTHORIZED);
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "pointEventAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        try {
            Slice<GetPointHistoryResponse> findPointHistories = pointHistoryRepositoryImpl.findPointHistory(userId, pageable);
            return findPointHistories;
        }
        catch (Exception e) {
            throw new CustomException(Result.NOT_FOUND_POINTHISTORY);
        }
    }
}
