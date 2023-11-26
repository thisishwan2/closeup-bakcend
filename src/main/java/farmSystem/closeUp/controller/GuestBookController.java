package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.guestbook.request.PostGuestBooksRequest;
import farmSystem.closeUp.dto.guestbook.response.GetGuestBooksResponse;
import farmSystem.closeUp.dto.guestbook.response.PostGuestBooksResponse;
import farmSystem.closeUp.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService guestBookService;

    // 크리에이터 방명록 조회(무한 스크롤)
    @GetMapping("/user/{creatorId}/guestbooks")
    public CommonResponse<Slice<GetGuestBooksResponse>> getGuestBooks(@PathVariable("creatorId") Long creatorId, Pageable pageable) {
        return CommonResponse.success(guestBookService.getGuestBooks(creatorId, pageable));
    }

    // 크리에이터 방명록 작성
    @PostMapping("/user/{creatorId}/guestbooks")
    public CommonResponse<PostGuestBooksResponse> postGuestbook (
            @PathVariable("creatorId") Long creatorId,
            @RequestBody PostGuestBooksRequest request) {
        return CommonResponse.success(guestBookService.postGuestBook(creatorId, request));
    }
}