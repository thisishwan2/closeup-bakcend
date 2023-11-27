package farmSystem.closeUp.repository.pointHistory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import farmSystem.closeUp.domain.PointHistory;
import farmSystem.closeUp.dto.pointHistory.response.GetPointHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static farmSystem.closeUp.domain.QPointHistory.pointHistory;

@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<GetPointHistoryResponse> findPointHistory(Long userId, Pageable pageable) {
        List<PointHistory> pointHistories = queryFactory
                .selectFrom(pointHistory)
                .where(pointHistory.user.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(pointHistory.pointEventAt.desc())
                .fetch();

        List<GetPointHistoryResponse> result = new ArrayList<>();

        for (PointHistory pointHistory : pointHistories) {
            result.add(GetPointHistoryResponse.of(pointHistory.getPointHistoryId(), pointHistory.getPlusPoint(), pointHistory.getMinusPoint(), pointHistory.getPointHistoryName(), pointHistory.getPointEventAt()));
        }

        return new SliceImpl<>(result, pageable, hasNextPage(result, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<GetPointHistoryResponse> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
