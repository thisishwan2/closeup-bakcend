package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.domain.GuestBook;
import farmSystem.closeUp.domain.Heart;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.dto.heart.response.DeleteHeartResponse;
import farmSystem.closeUp.dto.heart.response.PostHeartResponse;
import farmSystem.closeUp.repository.guestbook.GuestBookRepository;
import farmSystem.closeUp.repository.heart.HeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HeartService {

    private final UserService userService;
    private final GuestBookRepository guestBookRepository;
    private final HeartRepository heartRepository;

    // 크리에이터 - 방명록 반응 남기기 (좋아요)
    @Transactional
    public PostHeartResponse postHeart(Long guestbookId) {
        User user = userService.getCurrentUser();
        GuestBook guestBook = guestBookRepository.findById(guestbookId).orElseThrow(()-> new CustomException(Result.NOTFOUND_GUESTBOOK));

        // 이미 좋아요(반응)를 한 경우
        Heart isHeart = heartRepository.findByGuestBook(guestBook);
        if(isHeart!=null) {
            throw  new CustomException(Result.DUPLICATED_HEART);
        }

        // 크리에이터 자신의 방명록이 아닌 경우
        if (!guestBook.getCreator().getUserId().equals(user.getUserId())) {
            throw new CustomException(Result.NOT_AUTHORIZED_CREATOR);
        }

        Heart heart = Heart.builder()
                .guestBook(guestBook)
                .build();

        heartRepository.save(heart);

        heart.setGuestBook(guestBook);
        guestBook.setHeart(heart);

        return PostHeartResponse.builder().heartId(heart.getHeartId()).build();

    }

    // 크리에이터 - 방명록 반응 삭제
    @Transactional
    public DeleteHeartResponse deleteHeart(Long heartId) {
        User user = userService.getCurrentUser();
        Heart heart = heartRepository.findById(heartId).orElseThrow(()-> new CustomException(Result.NOTFOUND_HEART));

        if(!heart.getGuestBook().getCreator().getUserId().equals(user.getUserId())) {
            throw new CustomException(Result.NOT_AUTHORIZED_CREATOR);
        }

        heartRepository.delete(heart);

        return DeleteHeartResponse.builder()
                .heartId(heart.getHeartId())
                .build();
    }
}
