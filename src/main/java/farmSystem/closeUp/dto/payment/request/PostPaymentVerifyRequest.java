package farmSystem.closeUp.dto.payment.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostPaymentVerifyRequest {

    private String imp_uid;
    private String merchantId;
    private String amount;


}
