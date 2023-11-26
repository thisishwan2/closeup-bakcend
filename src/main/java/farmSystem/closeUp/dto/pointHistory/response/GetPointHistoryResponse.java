package farmSystem.closeUp.dto.pointHistory.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetPointHistoryResponse {

    private Long pointHistoryId;
    private Long plusPoint;
    private Long minusPoint;
    private String pointHistoryName;
    private LocalDateTime pointEventAt;

    public static GetPointHistoryResponse of(Long pointHistoryId, Long plusPoint, Long minusPoint, String pointHistoryName, LocalDateTime pointEventAt){
        return GetPointHistoryResponse.builder()
                .pointHistoryId(pointHistoryId)
                .plusPoint(plusPoint)
                .minusPoint(minusPoint)
                .pointHistoryName(pointHistoryName)
                .pointEventAt(pointEventAt)
                .build();
    }

}
