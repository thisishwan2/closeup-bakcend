package farmSystem.closeUp.dto.creator.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PostCreatorSettingRequest {
    private List<Long> interestIds;
    private Long platformId;
}
