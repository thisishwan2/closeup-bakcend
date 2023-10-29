package farmSystem.closeUp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInterestRequest {
    Long userId;
    Long[] genreId;
}
