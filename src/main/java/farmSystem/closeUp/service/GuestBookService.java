package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.domain.GuestBook;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.dto.guestbook.request.PatchGuestBooksRequest;
import farmSystem.closeUp.dto.guestbook.request.PostGuestBooksRequest;
import farmSystem.closeUp.dto.guestbook.response.DeleteGuestBooksResponse;
import farmSystem.closeUp.dto.guestbook.response.GetGuestBooksResponse;
import farmSystem.closeUp.dto.guestbook.response.PatchGuestBooksResponse;
import farmSystem.closeUp.dto.guestbook.response.PostGuestBooksResponse;
import farmSystem.closeUp.repository.guestbook.GuestBookRepository;
import farmSystem.closeUp.repository.guestbook.GuestBookRepositoryImpl;
import farmSystem.closeUp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestBookService {

    private final GuestBookRepository guestBookRepository;
    private final GuestBookRepositoryImpl guestBookRepositoryImpl;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public Slice<GetGuestBooksResponse> getGuestBooks(Long creatorId, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Slice<GetGuestBooksResponse> findGuestBooks = guestBookRepositoryImpl.findByGuestBooks(creatorId, pageable);

        return findGuestBooks;
    }

    // 방명록 작성
    @Transactional
    public PostGuestBooksResponse postGuestBook(Long creatorId, PostGuestBooksRequest request) {
        User user = userService.getCurrentUser();

        GuestBook guestBook = GuestBook.builder()
                .content(request.getContent())
                .build();

        User creator = userRepository.findById(creatorId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));
        guestBook.setUser(user);
        guestBook.setCreator(creator);

        guestBookRepository.save(guestBook);

        return PostGuestBooksResponse.builder()
                .guestBookId(guestBook.getGuestBookId())
                .content(guestBook.getContent())
                .build();
    }

    // 방명록 수정
    @Transactional
    public PatchGuestBooksResponse patchGuestBook(Long guestbookId, PatchGuestBooksRequest request) {
        User user = userService.getCurrentUser();
        GuestBook guestBook = guestBookRepository.findById(guestbookId).orElseThrow(() -> new CustomException(Result.NOTFOUND_GUESTBOOK));

        if(!guestBook.getUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(Result.NOT_AUTHORIZED);
        }

        guestBook.setContent(request.getContent());

        guestBookRepository.save(guestBook);

        return PatchGuestBooksResponse.builder()
                .guestBookId(guestBook.getGuestBookId())
                .content(guestBook.getContent())
                .build();
    }

    // 방명록 삭제
    @Transactional
    public DeleteGuestBooksResponse deleteGuestBook(Long guestbookId) {
        User user = userService.getCurrentUser();
        GuestBook guestBook = guestBookRepository.findById(guestbookId).orElseThrow(() -> new CustomException(Result.NOTFOUND_GUESTBOOK));

        if(!guestBook.getUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(Result.NOT_AUTHORIZED);
        }

        guestBookRepository.delete(guestBook);

        return DeleteGuestBooksResponse.builder()
                .guestBookId(guestBook.getGuestBookId())
                .build();
    }

}