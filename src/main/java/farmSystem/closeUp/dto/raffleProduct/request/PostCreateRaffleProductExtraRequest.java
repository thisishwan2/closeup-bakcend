package farmSystem.closeUp.dto.raffleProduct.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRaffleProductExtraRequest {
    private String file;
}
