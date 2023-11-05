package farmSystem.closeUp.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.dto.user.response.GetSearchCreatorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static farmSystem.closeUp.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public Slice<GetSearchCreatorResponse> findByPlatform(Long platformId, Pageable pageable) {
        List<User> findCreators = queryFactory
                .selectFrom(user)
                .where(user.platform.platformId.eq(platformId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(user.createdAt.desc())
                .fetch();

        List<GetSearchCreatorResponse> result = new ArrayList<>();

        for (User findCreator : findCreators) {
            result.add(GetSearchCreatorResponse.of(findCreator.getUserId(), findCreator.getNickName(), findCreator.getProfileImageUrl(), findCreator.getProfileComment()));
        }

        return new SliceImpl<>(result, pageable, hasNextPage(result, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<GetSearchCreatorResponse> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
